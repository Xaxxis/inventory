package id.xaxxis.inventory.service.master.suplier;

import id.xaxxis.inventory.dao.master.suplier.SuplierDao;
import id.xaxxis.inventory.dao.master.suplier.SuplierTypeDao;
import id.xaxxis.inventory.entity.master.suplier.Suplier;
import id.xaxxis.inventory.entity.master.suplier.SuplierType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuplierServiceImpl implements SuplierService {

    private final SuplierDao suplierDao;
    private final SuplierTypeDao suplierTypeDao;

    @Autowired
    public SuplierServiceImpl(SuplierDao suplierDao,
                              SuplierTypeDao suplierTypeDao) {
        this.suplierDao = suplierDao;
        this.suplierTypeDao = suplierTypeDao;
    }

    @Override
    public List<Suplier> findAll() {
        return suplierDao.findAll();
    }

    @Override
    public List<Suplier> findAllByMasterLocation(String locationId) {
        return suplierDao.findAllByMasterLocation_LocationId(locationId);
    }

    @Override
    public Suplier findBySuplierId(String suplierId) {
        return suplierDao.findBySuplierId(suplierId);
    }

    @Override
    public Suplier findBySuplierPhone(String suplierPhone) {
        return suplierDao.findBySuplierPhone(suplierPhone);
    }

    @Override
    public Suplier createSuplier(Suplier suplier) {
        return suplierDao.save(suplier);
    }

    @Override
    public Suplier saveSuplier(Suplier suplier) {
        return suplierDao.save(suplier);
    }

    @Override
    public List<SuplierType> findAllSuplierType() {
        return suplierTypeDao.findAll();
    }

    @Override
    public Optional<SuplierType> findSuplierTypeById(String id) {
        return suplierTypeDao.findById(id);
    }

    @Override
    public SuplierType findSuplierTypeByType(String type) {
        return suplierTypeDao.findByType(type);
    }


}