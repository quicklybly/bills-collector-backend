package com.quicklybly.billscollector.repository

import com.quicklybly.billscollector.entity.BillUsage
import org.springframework.data.jpa.repository.JpaRepository

interface BillUsageRepository : JpaRepository<BillUsage, Long>
