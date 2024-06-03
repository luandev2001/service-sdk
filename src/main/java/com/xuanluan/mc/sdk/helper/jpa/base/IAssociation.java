package com.xuanluan.mc.sdk.helper.jpa.base;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.Collection;

public interface IAssociation<T, X, C> extends IBase {

    Join<T, X> getJoin();

    Collection<C> getColumns();
}
