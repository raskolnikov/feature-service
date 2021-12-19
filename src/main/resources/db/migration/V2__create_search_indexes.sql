CREATE INDEX users_simple_search_index ON users USING GIN ( to_tsvector('simple', first_name || ' ' || last_name || ' ' || mobile_number || ' ' || email) );
CREATE INDEX features_simple_search_index ON features USING GIN ( to_tsvector('simple', name) );

CREATE INDEX permissions_user_id_index ON permissions USING btree (user_id);
CREATE INDEX permissions_feature_id_index ON permissions USING btree (feature_id);


