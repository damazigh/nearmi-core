package org.nearmi.core.repository;

import org.nearmi.core.mongo.document.MiProUser;
import org.springframework.stereotype.Repository;

@Repository
public interface MiProUserRepository extends CoreUserRepository<MiProUser> {
}
