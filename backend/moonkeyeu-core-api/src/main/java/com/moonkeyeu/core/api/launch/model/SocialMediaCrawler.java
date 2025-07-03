package com.moonkeyeu.core.api.launch.model;

import lombok.Getter;

@Getter
public enum SocialMediaCrawler {
    FACEBOOK("facebookexternalhit"),
    TWITTER("twitterbot"),
    LINKEDIN("linkedinbot"),
    SLACK("slackbot"),
    DISCORD("discordbot"),
    STEAM("SteamChatURLLookup");

    private final String identifier;

    SocialMediaCrawler(String identifier) {
        this.identifier = identifier;
    }
}
