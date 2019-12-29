package com.shortlink.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MapLink.class)
public abstract class MapLink_ {

	public static volatile SingularAttribute<MapLink, Long> opened;
	public static volatile SingularAttribute<MapLink, Long> id;
	public static volatile SingularAttribute<MapLink, String> key;
	public static volatile SingularAttribute<MapLink, String> url;

	public static final String OPENED = "opened";
	public static final String ID = "id";
	public static final String KEY = "key";
	public static final String URL = "url";

}

