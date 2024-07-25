package com.selincengiz.ritmo.domain.usecase.app_entry

import com.selincengiz.ritmo.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}