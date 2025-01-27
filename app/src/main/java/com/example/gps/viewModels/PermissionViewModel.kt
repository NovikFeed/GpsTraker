package com.example.gps.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gps.model.repositoryes.PermissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val permissionRepository: PermissionRepository,
    private val app: Application
) : ViewModel() {
    private val _havePermissions = MutableStateFlow(false)
    val havePermission = _havePermissions

    init {
        checkPermission()
    }

    private fun checkPermission() {
        viewModelScope.launch {
            permissionRepository.getPermission().collect {
                _havePermissions.value = it
            }
        }
    }

    fun stopCheckPermission() {
        permissionRepository.clear()
    }

    fun goToSettings() {
        permissionRepository.goToSettings()
    }
}