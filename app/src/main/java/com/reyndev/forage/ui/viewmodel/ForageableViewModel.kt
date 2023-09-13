package com.reyndev.forage.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.reyndev.forage.data.ForageableDao
import com.reyndev.forage.model.Forageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Shared [ViewModel] to provide data to the [ForageableListFragment], [ForageableDetailFragment],
 * and [AddForageableFragment] and allow for interaction the the [ForageableDao]
 */

// TODO: pass a ForageableDao value as a parameter to the view model constructor
class ForageableViewModel(
    // Pass dao here
    private val dao: ForageableDao
) : ViewModel() {
    // TODO: create a property to set to a list of all forageables from the DAO
    val forageables: LiveData<List<Forageable>> = dao.getForageables().asLiveData()

    // TODO : create method that takes id: Long as a parameter and retrieve a Forageable from the
    //  database by id via the DAO.
    fun getForageable(id: Long): LiveData<Forageable> = dao.getForageable(id).asLiveData()

    fun addForageable(
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )

        // TODO: launch a coroutine and call the DAO method to add a Forageable to the database within it
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(forageable)
        }
    }

    fun updateForageable(
        id: Long,
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            id = id,
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: call the DAO method to update a forageable to the database here
            dao.update(forageable)
        }
    }

    fun deleteForageable(forageable: Forageable) {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: call the DAO method to delete a forageable to the database here
            dao.delete(forageable)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}

// TODO: create a view model factory that takes a ForageableDao as a property and
//  creates a ForageableViewModel
class ForageableViewModelFactory(private val dao: ForageableDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ForageableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForageableViewModel(dao) as T
        }

        throw IllegalArgumentException("Unkown ViewModel")
    }
}