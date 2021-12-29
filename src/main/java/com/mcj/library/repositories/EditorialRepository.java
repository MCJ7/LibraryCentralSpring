
package com.mcj.library.repositories;

import com.mcj.library.entities.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Integer>{

    /**
     *
     * @param id
     */
    @Modifying
    @Query("UPDATE Editorial e SET e.register = true WHERE e.id = :id")
    public void enable(@Param("id")Integer id);

    /**
     *
     * @param id
     * @param nam
     */
    @Modifying
    @Query("UPDATE Editorial e SET e.name = :nam WHERE e.id = :id")
    public void update(@Param("id")Integer id,@Param("nam")String nam);
}
