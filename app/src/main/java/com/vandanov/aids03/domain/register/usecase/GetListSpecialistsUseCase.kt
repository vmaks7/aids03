package com.vandanov.aids03.domain.register.usecase

import com.vandanov.aids03.domain.register.entity.appointmentDateTime.Appointment
import com.vandanov.aids03.domain.register.repository.RegisterRepository

class GetListSpecialistsUseCase(private val repository: RegisterRepository) {

    suspend operator fun invoke() : Appointment {
        return repository.getListSpecialists()
    }

}