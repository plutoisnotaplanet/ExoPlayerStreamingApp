package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player

import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.onFailure
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.onSuccess
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.success
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases.ExoPlayerUseCase
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common.BaseViewModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.QualityItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val exoPlayerUseCase: ExoPlayerUseCase
): BaseViewModel<PlayerAction, PlayerViewState>() {

    private val currentQualityMap = mutableMapOf<QualityItem, Int>()
    private var currentSelectedItem = QualityItem.AUTO

    fun getChannelById(channelId: Int) {
        viewModelScope.launchOnIo {
            exoPlayerUseCase.getChannelDataById(channelId).let {
                _viewStateFlow.emit(it)
            }
        }
    }

    fun addInQualityMap(qualityItem: QualityItem, qualityIndex: Int) {
        if (qualityItem.positionInList == currentSelectedItem.positionInList) {
            qualityItem.isSelected = true
            currentQualityMap[qualityItem] = qualityIndex
        } else {
            currentQualityMap[qualityItem] = qualityIndex
        }
    }

    fun setSelectedQuality(qualityItem: QualityItem) {
        currentSelectedItem = qualityItem
        currentQualityMap.forEach { (key, value) ->
            key.isSelected = key.positionInList == qualityItem.positionInList
        }
    }

    fun getQualityMap() = currentQualityMap

}