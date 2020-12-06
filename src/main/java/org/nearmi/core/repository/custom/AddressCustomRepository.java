package org.nearmi.core.repository.custom;

import org.nearmi.core.mongo.document.shopping.Address;
import org.nearmi.core.mongo.document.shopping.Shop;

import java.util.Collection;

public interface AddressCustomRepository {
    /**
     * search shop that are under a given perimeter (see param: {@code nearmi.config.max-distance}
     *
     * @param longitude representing longitude of a given point (in most case consumer address)
     * @param latitude  representing latitude of a given point (in most case consumer address)
     * @return a list of {@link Shop}
     */
    Collection<Address> findByLocation(float longitude, float latitude);

}
