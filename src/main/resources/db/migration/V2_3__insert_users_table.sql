INSERT INTO `users`(`id`, `language`, `login`, `firstname`, `lastname`, `email`, `password`, `role`, `created_at`)
VALUES (1, 'fr', 'bob', 'Bob', 'Sull', 'bob@sull.com','$2a$12$EyDxX5wWsrJGIyo2MFIRR.LqLmmqssUdPgwasMrG7TvZmjW9ECeLW','admin',NOW()),
       (2, 'en', 'anna', 'Anna', 'Lyse', 'anna.lyse@sull.com','$2a$12$EyDxX5wWsrJGIyo2MFIRR.LqLmmqssUdPgwasMrG7TvZmjW9ECeLW','member',NOW());