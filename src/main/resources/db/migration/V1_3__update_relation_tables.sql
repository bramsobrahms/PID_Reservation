-- TABLE Artiste_Types
ALTER TABLE artiste_types
    ADD COLUMN artist_id BIGINT NOT NULL AFTER id,
    ADD COLUMN type_id BIGINT NOT NULL AFTER artist_id;

-- TABLE Artiste_Type_Shows
ALTER TABLE artiste_type_shows
    ADD COLUMN artiste_type_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER artiste_type_id;

-- TABLE Locations
ALTER TABLE locations
    ADD COLUMN locality_id BIGINT NOT NULL AFTER id;

-- TABLE Representation_Reservations
ALTER TABLE representation_reservations
    ADD COLUMN price_id BIGINT NOT NULL AFTER id,
    ADD COLUMN representation_id BIGINT NOT NULL AFTER price_id,
    ADD COLUMN reservation_id BIGINT NOT NULL AFTER representation_id;

-- TABLE Representations
ALTER TABLE representations
    ADD COLUMN location_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER location_id;

-- TABLE Shows
ALTER TABLE shows
    ADD COLUMN location_id BIGINT NOT NULL AFTER id;

-- TABLE Reservations
ALTER TABLE reservations
    ADD COLUMN user_id BIGINT NOT NULL AFTER id;

-- TABLE Reviews
ALTER TABLE reviews
    ADD COLUMN show_id BIGINT NOT NULL AFTER id,
    ADD COLUMN user_id BIGINT NOT NULL AFTER show_id;

-- TABLE price_show
ALTER TABLE price_shows
    ADD COLUMN price_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER price_id;

-- Add Constraints for Locations
ALTER TABLE locations
    ADD CONSTRAINT locations_localities FOREIGN KEY (locality_id)
        REFERENCES locations (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Artiste_Types
ALTER TABLE artiste_types
    ADD CONSTRAINT fk_artiste_types_type FOREIGN KEY (type_id)
        REFERENCES types (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE artiste_types
    ADD CONSTRAINT fk_artiste_types_artist FOREIGN KEY (artist_id)
        REFERENCES artists (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Artiste_Type_Shows
ALTER TABLE artiste_type_shows
    ADD CONSTRAINT fk_artiste_type_show_artiste_type FOREIGN KEY (artiste_type_id)
        REFERENCES artiste_types (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE artiste_type_shows
    ADD CONSTRAINT fk_artiste_type_show_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Representation_Reservations
ALTER TABLE representation_reservations
    ADD CONSTRAINT fk_representation_reservations_representation FOREIGN KEY (representation_id)
        REFERENCES representations (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE representation_reservations
    ADD CONSTRAINT fk_representation_reservations_price FOREIGN KEY (price_id)
        REFERENCES prices (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE representation_reservations
    ADD CONSTRAINT fk_representation_reservations_reservation FOREIGN KEY (reservation_id)
        REFERENCES reservations (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Representations
ALTER TABLE representations
    ADD CONSTRAINT fk_representations_location_id FOREIGN KEY (location_id)
        REFERENCES locations (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Shows
ALTER TABLE shows
    ADD CONSTRAINT fk_shows_location FOREIGN KEY (location_id)
        REFERENCES locations (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Reservations
ALTER TABLE reservations
    ADD CONSTRAINT fk_reservations_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Reviews
ALTER TABLE reviews
    ADD CONSTRAINT fk_reviews_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE reviews
    ADD CONSTRAINT fk_reviews_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Locations (Locality)
ALTER TABLE locations
    ADD CONSTRAINT fk_locations_locality FOREIGN KEY (locality_id)
        REFERENCES localities (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Price_Shows
ALTER TABLE price_shows
    ADD CONSTRAINT fk_price_show_price FOREIGN KEY (price_id)
        REFERENCES prices (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE price_shows
    ADD CONSTRAINT fk_price_show_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;