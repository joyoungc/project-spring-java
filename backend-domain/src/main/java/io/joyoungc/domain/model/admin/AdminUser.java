/*
 * Created by Aiden Jeong on 2024.06.01
 */
package io.joyoungc.domain.model.admin;

public class AdminUser {

    private long id;

    public enum Status {
        ACTIVE, INACTIVE, DELETED
    }
}
