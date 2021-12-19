CREATE TABLE IF NOT EXISTS users (
             id bigint NOT NULL,
             first_name character varying(50) NOT NULL,
             last_name character varying(50) NOT NULL,
             mobile_number character varying(20),
             email character varying(50) NOT NULL,
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
             description character varying(200) NOT NULL,
             status character varying(20) NOT NULL default 'active',
             created_at timestamp NOT NULL,
             updated_at timestamp NOT NULL,
             created_by bigint NOT NULL,
             updated_by bigint NOT NULL,
             CONSTRAINT permissions_pk PRIMARY KEY (id));










