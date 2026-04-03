package az.khayalsharifli.data.websocket

data class StockInfo(
    val symbol: String,
    val name: String,
    val description: String,
    val basePrice: Double
)

object StockDataProvider {

    val stocks = listOf(
        StockInfo("AAPL", "Apple Inc.", "Apple designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories.", 178.50),
        StockInfo("GOOG", "Alphabet Inc.", "Alphabet is a multinational conglomerate and parent company of Google, specializing in internet services and AI.", 141.20),
        StockInfo("TSLA", "Tesla Inc.", "Tesla designs, develops, manufactures, and sells electric vehicles, energy storage, and solar energy products.", 245.80),
        StockInfo("AMZN", "Amazon.com Inc.", "Amazon is a multinational technology company focusing on e-commerce, cloud computing, and artificial intelligence.", 185.60),
        StockInfo("MSFT", "Microsoft Corp.", "Microsoft develops and supports software, services, devices, and solutions worldwide.", 415.30),
        StockInfo("NVDA", "NVIDIA Corp.", "NVIDIA designs GPU-accelerated computing platforms for gaming, professional visualization, data centers, and automotive.", 875.40),
        StockInfo("META", "Meta Platforms Inc.", "Meta builds technologies that help people connect, find communities, and grow businesses through social platforms.", 505.75),
        StockInfo("NFLX", "Netflix Inc.", "Netflix is a streaming entertainment service offering TV series, documentaries, feature films, and mobile games.", 628.90),
        StockInfo("AMD", "Advanced Micro Devices", "AMD designs and integrates technology that powers computing and graphics solutions for businesses and consumers.", 162.30),
        StockInfo("INTC", "Intel Corp.", "Intel designs and manufactures computing and networking components, including CPUs and chipsets.", 43.80),
        StockInfo("CRM", "Salesforce Inc.", "Salesforce provides cloud-based enterprise software focused on customer relationship management.", 272.40),
        StockInfo("ORCL", "Oracle Corp.", "Oracle provides cloud-based and license software products, including database management systems.", 128.60),
        StockInfo("PYPL", "PayPal Holdings", "PayPal operates a technology platform for digital payments and financial services.", 63.50),
        StockInfo("UBER", "Uber Technologies", "Uber develops and operates technology applications for ride-hailing, food delivery, and freight.", 72.30),
        StockInfo("SHOP", "Shopify Inc.", "Shopify provides a cloud-based commerce platform designed for small and medium-sized businesses.", 78.90),
        StockInfo("SQ", "Block Inc.", "Block builds tools to empower businesses and individuals to participate in the economy through payments.", 82.15),
        StockInfo("SNAP", "Snap Inc.", "Snap operates as a technology and social media company, known for Snapchat and augmented reality products.", 15.60),
        StockInfo("SPOT", "Spotify Technology", "Spotify provides audio streaming subscription services including music, podcasts, and audiobooks.", 295.40),
        StockInfo("ZM", "Zoom Video Comm.", "Zoom provides a communications platform using video, voice, chat, and content sharing.", 68.20),
        StockInfo("COIN", "Coinbase Global", "Coinbase operates a cryptocurrency exchange platform for buying, selling, and storing digital currencies.", 225.70),
        StockInfo("ROKU", "Roku Inc.", "Roku manufactures a variety of digital media players for video streaming.", 65.30),
        StockInfo("PINS", "Pinterest Inc.", "Pinterest operates as a visual discovery engine, helping users find inspiration for various projects.", 32.80),
        StockInfo("TWLO", "Twilio Inc.", "Twilio provides cloud communications platforms enabling developers to build messaging and voice applications.", 62.40),
        StockInfo("DDOG", "Datadog Inc.", "Datadog provides monitoring and security platform for cloud applications.", 125.80),
        StockInfo("NET", "Cloudflare Inc.", "Cloudflare provides cloud-based network services to secure and ensure reliability of internet properties.", 85.60)
    )
}
