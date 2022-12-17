alter table schedule alter column coef type numeric(19,4) USING coef::numeric(19,4);

alter table category drop column schedule_id;

alter table schedule add column category_id uuid CONSTRAINT fk_category_id REFERENCES category (id);