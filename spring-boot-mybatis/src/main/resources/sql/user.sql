CREATE TABLE user
(
    `id`    int NOT NULL AUTO_INCREMENT,
    `name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `sex`   tinyint NULL DEFAULT NULL,
    `phone` bigint NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;