package com.mateuszwiater.csc435.services

import com.mateuszwiater.csc435.domains.Preference
import grails.transaction.Transactional

@Transactional
class PreferenceService {

   def getPreference(UUID apiKey, long articleId) {
       return Optional.ofNullable(Preference.findByApiKeyAndArticleId(apiKey, articleId))
    }

    def getPreferences(final UUID apiKey) {
        return Preference.findAllByApiKey(apiKey)
    }
}
