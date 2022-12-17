alter table room
    add column if not exists reservation_room_id uuid
        CONSTRAINT fk_reservation_room_id REFERENCES reservation_room (id);
alter table client
    add column if not exists reservation_room_id uuid
        CONSTRAINT fk_reservation_room_id REFERENCES reservation_room (id);

