package com.example.demo.service;

import com.example.demo.entity.ZoneRestorationRecord;
import java.util.*;

public interface ZoneRestorationService {
    ZoneRestorationRecord restoreZone(ZoneRestorationRecord record);
    ZoneRestorationRecord getRecordById(Long id);
    List<ZoneRestorationRecord> getRecordsForZone(Long zoneId);
}
