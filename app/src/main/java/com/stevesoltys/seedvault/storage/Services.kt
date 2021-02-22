package com.stevesoltys.seedvault.storage

import org.calyxos.backup.storage.api.BackupObserver
import org.calyxos.backup.storage.api.RestoreObserver
import org.calyxos.backup.storage.api.StorageBackup
import org.calyxos.backup.storage.backup.BackupJobService
import org.calyxos.backup.storage.backup.BackupService
import org.calyxos.backup.storage.backup.NotificationBackupObserver
import org.calyxos.backup.storage.restore.NotificationRestoreObserver
import org.calyxos.backup.storage.restore.RestoreService
import org.koin.android.ext.android.inject

/*
test and debug with

  adb shell dumpsys jobscheduler |
  grep -A 23 -B 4 "Service: com.stevesoltys.seedvault/.storage.StorageBackupJobService"

force running with:

  adb shell cmd jobscheduler run -f com.stevesoltys.seedvault 0

 */
internal class StorageBackupJobService : BackupJobService(StorageBackupService::class.java)

internal class StorageBackupService : BackupService() {
    override val storageBackup: StorageBackup by inject()

    // use lazy delegate because context isn't available during construction time
    override val backupObserver: BackupObserver by lazy {
        NotificationBackupObserver(applicationContext)
    }
}

internal class StorageRestoreService : RestoreService() {
    override val storageBackup: StorageBackup by inject()

    // use lazy delegate because context isn't available during construction time
    override val restoreObserver: RestoreObserver by lazy {
        NotificationRestoreObserver(applicationContext)
    }
}
