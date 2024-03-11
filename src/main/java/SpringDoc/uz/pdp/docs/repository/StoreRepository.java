package SpringDoc.uz.pdp.docs.repository;

import SpringDoc.uz.pdp.docs.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StoreRepository extends JpaRepository<Store,Long> {

  @Modifying
  @Query("UPDATE Store s SET s.name = :name WHERE s.email = :email AND s.desc = :desc AND s.employeeCount = :employeeCount")
  void updateByNameAndEmailAndDescAndEmployeeCount(@Param("name") String name, @Param("email") String email, @Param("desc") String desc, @Param("employeeCount") int employeeCount);



}
