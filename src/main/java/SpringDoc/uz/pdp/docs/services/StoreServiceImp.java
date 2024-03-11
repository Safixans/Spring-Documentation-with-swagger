package SpringDoc.uz.pdp.docs.services;

import SpringDoc.uz.pdp.docs.dto.StoreDTO;
import SpringDoc.uz.pdp.docs.entities.Store;
import SpringDoc.uz.pdp.docs.exceptions.StoreNotFoundException;
import SpringDoc.uz.pdp.docs.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImp {

    private final StoreRepository storeRepository;

    public StoreServiceImp(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }


    public ResponseEntity<Void> create(Store store) {
        storeRepository.save(store);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Store> getById(Long id) {
        Store store = storeRepository
                .findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new StoreNotFoundException("Not FOund by given id"));
        return ResponseEntity.ok(store);
    }

    public ResponseEntity<List<Store>> getAll() {
        List<Store> all = storeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    public ResponseEntity<Void> update(StoreDTO dto, Long id) {
        Store oldOne = storeRepository
                .findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new StoreNotFoundException("Not FOund by given id"));

        storeRepository.updateByNameAndEmailAndDescAndEmployeeCount(
                oldOne.getName(),
                oldOne.getEmail(),
                oldOne.getDesc(),
                oldOne.getEmployeeCount());

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> delete(Long id) {
        storeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
