CREATE TABLE IF NOT EXISTS users (
             id bigint NOT NULL,
             first_name character varying(50) NOT NULL,
             last_name character varying(50) NOT NULL,
             mobile_number character varying(20),
             users_user_mobile_number_unique_index character varying(20) as (case status when 'DELETED' then null else mobile_number end) unique,
             email character varying(50) NOT NULL,
             users_email_name_unique_index character varying(50) as (case status when 'DELETED' then null else email end) unique,
             password character varying(100) NOT NULL,
             role character varying(50) NOT NULL,
             status character varying(20) NOT NULL default 'active',
             created_at timestamp NOT NULL,
             updated_at timestamp NOT NULL,
             created_by bigint NOT NULL,
             updated_by bigint NOT NULL,
             CONSTRAINT users_pk PRIMARY KEY (id));


CREATE TABLE IF NOT EXISTS features (
			id bigint NOT NULL,
			name character varying(100) NOT NULL,
			description character varying(200) NOT NULL,
            status character varying(20) NOT NULL default 'active',
            access_type character varying(20) NOT NULL default 'restricted',
            created_at timestamp NOT NULL,
            updated_at timestamp NOT NULL,
            created_by bigint NOT NULL,
            updated_by bigint NOT NULL,
			CONSTRAINT features_pk PRIMARY KEY (id));


CREATE TABLE IF NOT EXISTS permissions (
             id bigint NOT NULL,
             name character varying(100) NOT NULL,
             user_id bigint NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
             feature_id bigint NOT NULL REFERENCES features(id) ON DELETE RESTRICT,
             permissions_user_id_feature_id_unique_index character varying(50) as (case status when 'DELETED' then null else user_id end, case status when 'DELETED' then null else feature_id end) unique,
             description character varying(200) NOT NULL,
             status character varying(20) NOT NULL default 'active',
             created_at timestamp NOT NULL,
             updated_at timestamp NOT NULL,
             created_by bigint NOT NULL,
             updated_by bigint NOT NULL,
             CONSTRAINT permissions_pk PRIMARY KEY (id));










