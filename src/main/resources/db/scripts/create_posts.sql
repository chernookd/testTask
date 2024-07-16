CREATE TABLE posts (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user_posts FOREIGN KEY (user_id) REFERENCES users (id)
);
