import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {
        @Override
    @Transactional
    public Zone createZone(Zone zone) { ... }

    @Override
    @Transactional
    public Zone updateZone(Long id, Zone zone) { ... }

    @Override
    @Transactional(readOnly = true)
    public Zone getZoneById(Long id) { ... }

    @Override
    @Transactional(readOnly = true)
    public List<Zone> getAllZones() { ... }

    @Override
    @Transactional
    public void deactivateZone(Long id) { ... }

