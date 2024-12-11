package com.example.newtechnews.data

import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.model.ArticleSource

val previewArticle = Article(
    url = "https://www.techradar.com/gaming/gaming-industry/naughty-dog-welcomes-god-of-war-ragnaroks-former-art-director-raf-grassetti",
    articleSource = ArticleSource(id = "techradar", name = "TechRadar"),
    author = "Demi Williams",
    title = "Naughty Dog welcomes God of War Ragnarok's former art director Raf Grassetti",
    description = "Grassetti worked at Sony Santa Monica for 10 years",
    urlToImage = "https://cdn.mos.cms.futurecdn.net/h878r5ze5QG9x72s424QYA-1200-80.jpg",
    publishedAt = "2024-12-10T13:31:28Z",
    content = "<ul><li>God of War Ragnarok's former art director Raf Grassetti has joined Naughty Dog</li><li>Grassetti was at Sony Santa Monica for 10 years before leaving to work at Netflix Games' Team Blue, which… [+1975 chars]"
)
//val mock_articles = listOf(
//    Article(
//        articleSource = ArticleSource(id = null, name = "9to5google.com"),
//        author = "Ben Schoon",
//        title = "Samsung Galaxy phones finally switch to vertically scrolling app drawer in One UI 7 - 9to5Google",
//        description = "Samsung has updated the app drawer for Galaxy smartphones in One UI 7 to use a vertically scrolling list instead of pages.",
//        url = "http://9to5google.com/2024/12/05/samsung-galaxy-vertical-app-drawer-one-ui-7/",
//        urlToImage = "https://i0.wp.com/9to5google.com/wp-content/uploads/sites/4/2024/12/samsung-one-ui-7-vertical-app-drawer-1.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1",
//        publishedAt = "2024-12-05T14:55:00Z",
//        content = "After years of sticking with an app drawer that uses pages..."
//    ),
//    Article(
//        articleSource = ArticleSource(id = null, name = "Android Police"),
//        author = "Nathan Drescher",
//        title = "Google Photos makes it dead-simple to pull your files off of the cloud - Android Police",
//        description = "Remove photos backups without losing files",
//        url = "https://www.androidpolice.com/google-photos-undo-backup/",
//        urlToImage = "https://static1.anpoimages.com/wordpress/wp-content/uploads/2024/08/google-photos-3-ap24-hero.jpg",
//        publishedAt = "2024-12-05T00:50:00Z",
//        content = "Key Takeaways..."
//    ),
//    Article(
//        articleSource = ArticleSource(id = null, name = "9to5google.com"),
//        author = "Abner Li",
//        title = "Google teases ‘AI Mode’ as new way to talk to Search [APK Insight] - 9to5Google",
//        description = "Google is readying some sort of “AI Mode” for the Search app...",
//        url = "http://9to5google.com/2024/12/04/google-search-ai-mode/",
//        urlToImage = "https://i0.wp.com/9to5google.com/wp-content/uploads/sites/4/2023/12/google-search-android-2.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1",
//        publishedAt = "2024-12-05T00:04:00Z",
//        content = "Google is readying some sort of AI Mode..."
//    ),
//    Article(
//        articleSource = ArticleSource(id = "the-verge", name = "The Verge"),
//        author = "Jay Peters",
//        title = "A Google AI model can create playable virtual worlds on the fly. - The Verge",
//        description = "",
//        url = "https://www.theverge.com/2024/12/4/24313409/a-google-ai-model-can-create-playable-virtual-worlds-on-the-fly",
//        urlToImage = "https://cdn.vox-cdn.com/uploads/chorus_asset/file/23966628/the_verge_social_share.png",
//        publishedAt = "2024-12-04T20:06:24Z",
//        content = "Posted Dec 4, 2024 At 8:06 PM UTC..."
//    ),
//    Article(
//        articleSource = ArticleSource(id = null, name = "9to5google.com"),
//        author = "Abner Li",
//        title = "Gemini Extensions for Messages, Phone, and WhatsApp rolling out - 9to5Google",
//        description = "Google is rolling out the Messages, Phone, and WhatsApp Gemini Extensions...",
//        url = "http://9to5google.com/2024/12/04/gemini-extensions-messages-phone/",
//        urlToImage = "https://i0.wp.com/9to5google.com/wp-content/uploads/sites/4/2024/04/Gemini-pop-up-on-Pixel-8.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1",
//        publishedAt = "2024-12-04T19:26:00Z",
//        content = "Following the wide Utilities rollout..."
//    ),
//
//    Article(
//        articleSource = ArticleSource(id = null, name = "9to5google.com"),
//        author = "Ben Schoon",
//        title = "OnePlus 13 is the latest Android flagship to ignore Qi2 - 9to5Google",
//        description = "The OnePlus 13 is geared up for a global release...",
//        url = "http://9to5google.com/2024/12/04/oneplus-13-qi2-not-supported/",
//        urlToImage = "https://i0.wp.com/9to5google.com/wp-content/uploads/sites/4/2024/12/oneplus-13-colors-1.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1",
//        publishedAt = "2024-12-04T15:25:00Z",
//        content = "The OnePlus 13 is geared up for a global release..."
//    ),
//    Article(
//        articleSource = ArticleSource(id = null, name = "Forbes"),
//        author = "Davey Winder",
//        title = "Google Confirms New Gmail Security Surprise—And It’s So Simple - Forbes",
//        description = "Not all security features are complicated...",
//        url = "https://www.forbes.com/sites/daveywinder/2024/12/04/google-confirms-new-gmail-security-surprise-and-its-so-simple/",
//        urlToImage = "https://imageio.forbes.com/specials-images/imageserve/674d7f9e957cf9c0d3466b95/0x0.jpg?format=jpg&height=900&width=1600&fit=bounds",
//        publishedAt = "2024-12-04T12:06:53Z",
//        content = "Google is making it easier to prevent a nasty CC surprise..."
//    ),
//    Article(
//        articleSource = ArticleSource(id = null, name = "Gizmodo.com"),
//        author = "Florence Ion",
//        title = "Google Maps Waze Alerts Are Helpful for Holiday Driving...",
//        description = "The Waze alerts on Google Maps...",
//        url = "https://gizmodo.com/google-maps-waze-alerts-are-helpful-for-holiday-driving-even-if-they-are-distracting-2000533808",
//        urlToImage = "https://gizmodo.com/app/uploads/2024/12/Google-Maps_New-UI.jpg",
//        publishedAt = "2024-12-04T12:00:36Z",
//        content = "I’ve been driving around more than usual this holiday season..."
//    ),
//    Article(
//        articleSource = ArticleSource(id = null, name = "Android Police"),
//        author = "Chris Thomas",
//        title = "Banking apps can now require you to install Android security updates - Android Police",
//        description = "Google's Play Integrity upgrades will soon let app developers...",
//        url = "https://www.androidpolice.com/apps-can-require-recent-android-security-updates-play-integrity/",
//        urlToImage = "https://static1.anpoimages.com/wordpress/wp-content/uploads/wm/2024/10/security-updates.jpg",
//        publishedAt = "2024-12-04T01:19:00Z",
//        content = "Key Takeaways..."
//    )
//)
