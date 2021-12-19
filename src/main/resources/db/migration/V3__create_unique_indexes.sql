CREATE UNIQUE INDEX users_email_name_unique_index ON users (lower(email)) where upper(status) <> 'DELETED';
CREATE UNIQUE INDEX users_user_mobile_number_unique_index ON users (mobile_number) where upper(status) <> 'DELETED';
CREATE UNIQUE INDEX permissions_user_id_feature_id_unique_index ON permissions (user_id, feature_id) where upper(status) <> 'DELETED'

