package com.users.crud.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@DynamicUpdate(true)
@DynamicInsert(true)
@Table(name="user")
@Entity
public class User extends Person {

}
