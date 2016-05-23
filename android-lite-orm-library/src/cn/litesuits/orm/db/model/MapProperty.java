package cn.litesuits.orm.db.model;

import java.lang.reflect.Field;

import cn.litesuits.orm.db.annotation.Mapping;

/**
 * 映射关系
 *
 * @author MaTianyu
 *         2014-3-7下午11:16:19
 */
public class MapProperty extends Property {
    private static final long serialVersionUID = 1641409866866426637L;
    public static final String PRIMARYKEY = " PRIMARY KEY ";
    public Mapping.Relation relation;

    public MapProperty(Property p, Mapping.Relation relation) {
        this(p.column, p.field, relation);
    }

    private MapProperty(String column, Field field, Mapping.Relation relation) {
        super(column, field);
        this.relation = relation;
    }

    public boolean isToMany() {
        return relation == Mapping.Relation.ManyToMany || relation == Mapping.Relation.OneToMany;
    }

    public boolean isToOne() {
        return relation == Mapping.Relation.ManyToOne || relation == Mapping.Relation.OneToOne;
    }

}
