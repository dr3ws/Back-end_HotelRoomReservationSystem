package org.example.porvs.HotelRoomReservationSystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.porvs.HotelRoomReservationSystem.exception.HotelRoomException;
import org.example.porvs.HotelRoomReservationSystem.mapper.RoomMapper;
import org.example.porvs.HotelRoomReservationSystem.models.*;
import org.example.porvs.HotelRoomReservationSystem.repository.RoomRepository;
import org.example.porvs.HotelRoomReservationSystem.service.ReservationRoomService;
import org.example.porvs.HotelRoomReservationSystem.service.RoomService;
import org.example.porvs.hotelRoomReservationSystem.dto.Filter;
import org.example.porvs.hotelRoomReservationSystem.dto.OrderItem;
import org.example.porvs.hotelRoomReservationSystem.dto.Page;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationRequest;
import org.hibernate.Criteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository repository;
    private final RoomMapper roomMapper;
    private final ReservationRoomService reservationService;

    @Override
    public Room get(UUID roomId) {
        return repository.findFirstById(roomId).orElseThrow(() ->
                HotelRoomException.notFound("комната " + roomId + " не найдена"));
    }

    @Override
    public List<Room> findAllRooms() {
        return repository.findAll();
    }

    @Override
    public List<Room> findAllRoomsByFilter(Filter filter, Page page, List<OrderItem> orders) {
        var specificationForReservationRoom = getSpecification(filter);
        var sort = (orders == null || orders.isEmpty()) ?
                Sort.unsorted() :
                Sort.by(orders.stream()
                        .filter(item -> item.getValue() != null && StringUtils.isNotBlank(item.getField()))
                        .map(item -> item.getValue() == OrderItem.ValueEnum.ASC
                                ? Sort.Order.asc(item.getField()) : Sort.Order.desc(item.getField()))
                        .collect(Collectors.toList()));

        Pageable pageRequest =
                (page != null) ? PageRequest.of(page.getNumber(), page.getCount(), sort) : PageRequest.of(0, 1000, sort);

        var reservationRoomIds =
                repository.findAll(specificationForReservationRoom).stream().distinct().map(Room::getId).toList();
        var notReservationRoomIds =
                reservationRoomIds.isEmpty() ? repository.findAllIds() : repository.findAllNotReservedRoomIds(reservationRoomIds);
        var specification = getSpecification(filter, notReservationRoomIds);

        return repository.findAll(specification, pageRequest).getContent();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ReservationRoom reservation(UUID roomId, ReservationRequest reservationRequest) {
        var room = get(roomId);
        return reservationService.reservation(room, reservationRequest);
    }

    public Specification<Room> getSpecification(Filter filter) {
        Specification<Room> specification;
        specification = addCriteria(null, ((root, query, criteriaBuilder) -> {
            Join<Room, ReservationRoom> rootJoin = root.join("reservation");
            return criteriaBuilder.greaterThan(
                    rootJoin.get(ReservationRoom_.checkOutDate), filter.getCheckInDate());
        }));
        specification = addCriteria(specification, ((root, query, criteriaBuilder) -> {
            Join<Room, ReservationRoom> rootJoin = root.join("reservation");
            return criteriaBuilder.lessThan(
                    rootJoin.get(ReservationRoom_.checkInDate), filter.getCheckOutDate());
        }));

        return specification;
    }

    public Specification<Room> getSpecification(Filter filter, List<UUID> roomIds) {
        Specification<Room> specification = null;
        if (filter.getCategory() != null) {
            specification = addCriteria(null, ((root, query, criteriaBuilder) -> {
                Join<Room, ReservationRoom> rootJoin = root.join("category");
                return criteriaBuilder.equal(
                        rootJoin.get(Category_.NAME), filter.getCategory());
            }));
        }
        if (filter.getPlaceCount() != null) {
            specification = addCriteria(specification, ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(Room_.PLACES), filter.getPlaceCount())));
        }
        specification = addCriteria(specification, ((root, query, criteriaBuilder) -> root.get(Room_.ID).in(roomIds)));
        return specification;
    }

    private Specification<Room> addCriteria(Specification<Room> source, Specification<Room> additional) {
        return source != null ? source.and(additional) : additional;
    }

    private Specification<Room> orCriteria(Specification<Room> source, Specification<Room> additional) {
        return source != null ? source.or(additional) : additional;
    }
}
