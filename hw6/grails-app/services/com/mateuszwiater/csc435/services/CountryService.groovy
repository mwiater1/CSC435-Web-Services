package com.mateuszwiater.csc435.services

import com.mateuszwiater.csc435.domains.Country
import com.mateuszwiater.csc435.domains.Source
import grails.transaction.Transactional

@Transactional
class CountryService {

    def getCountry(String country) {
        return Optional.ofNullable(Source.findByCountry(country))
                .map({s -> new Country(s.getCountry())})
    }

   def getCountries() {
       return Source.where {}
               .projections {distinct 'country'}
               .list().stream()
               .map{s -> new Country(s as String)}
               .collect()
   }
}
