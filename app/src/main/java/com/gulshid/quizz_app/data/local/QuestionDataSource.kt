package com.gulshid.quizz_app.data.local

import com.gulshid.quizz_app.data.model.Difficulty
import com.gulshid.quizz_app.data.model.Question
import com.gulshid.quizz_app.data.model.QuizCategory

object QuestionDataSource {

    val categories = listOf(
        QuizCategory("science", "Science", "🔬", "Physics, Chemistry & Biology", 10, "#6C63FF", Difficulty.MEDIUM),
        QuizCategory("tech", "Technology", "💻", "Programming, AI & Gadgets", 10, "#FF6584", Difficulty.HARD),
        QuizCategory("history", "History", "📜", "World Events & Civilizations", 10, "#43B89C", Difficulty.MEDIUM),
        QuizCategory("geography", "Geography", "🌍", "Countries, Capitals & Maps", 10, "#FF9F43", Difficulty.EASY),
        QuizCategory("sports", "Sports", "⚽", "Athletes, Teams & Records", 10, "#54A0FF", Difficulty.EASY),
        QuizCategory("math", "Mathematics", "🧮", "Algebra, Geometry & Logic", 10, "#FF6B6B", Difficulty.HARD)
    )

    private val scienceQuestions = listOf(
        Question(1, "What is the chemical symbol for Gold?", listOf("Go", "Gd", "Au", "Ag"), 2, "Gold's symbol 'Au' comes from the Latin word 'Aurum'.", "science", Difficulty.EASY, 10),
        Question(2, "Which planet is known as the Red Planet?", listOf("Venus", "Jupiter", "Saturn", "Mars"), 3, "Mars appears red due to iron oxide (rust) on its surface.", "science", Difficulty.EASY, 10),
        Question(3, "What is the speed of light in vacuum?", listOf("299,792 km/s", "199,792 km/s", "399,792 km/s", "499,792 km/s"), 0, "Light travels at approximately 299,792 kilometers per second in a vacuum.", "science", Difficulty.MEDIUM, 15),
        Question(4, "How many bones are in the adult human body?", listOf("196", "206", "216", "226"), 1, "An adult human body has 206 bones. Babies are born with about 270-300 bones.", "science", Difficulty.MEDIUM, 15),
        Question(5, "What is the powerhouse of the cell?", listOf("Nucleus", "Ribosome", "Mitochondria", "Golgi apparatus"), 2, "Mitochondria produce ATP (energy) through cellular respiration.", "science", Difficulty.EASY, 10),
        Question(6, "Which element has atomic number 1?", listOf("Helium", "Lithium", "Hydrogen", "Oxygen"), 2, "Hydrogen is the simplest and most abundant element in the universe.", "science", Difficulty.EASY, 10),
        Question(7, "What is Newton's Second Law of Motion?", listOf("F = mv", "F = ma", "F = m/a", "F = a/m"), 1, "Force equals mass times acceleration (F = ma).", "science", Difficulty.MEDIUM, 15),
        Question(8, "DNA stands for?", listOf("Deoxyribonucleic Acid", "Diribonucleic Acid", "Deoxyribonitric Acid", "Dioxyribonucleic Acid"), 0, "DNA (Deoxyribonucleic Acid) carries genetic information.", "science", Difficulty.MEDIUM, 15),
        Question(9, "What is the boiling point of water at sea level?", listOf("90°C", "95°C", "100°C", "105°C"), 2, "Water boils at 100°C (212°F) at standard atmospheric pressure.", "science", Difficulty.EASY, 10),
        Question(10, "Which gas do plants absorb during photosynthesis?", listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"), 2, "Plants absorb CO₂ and release oxygen during photosynthesis.", "science", Difficulty.EASY, 10)
    )

    private val techQuestions = listOf(
        Question(11, "What does 'HTTP' stand for?", listOf("HyperText Transfer Protocol", "High Transfer Text Protocol", "HyperText Transmission Protocol", "High Text Transfer Protocol"), 0, "HTTP is the foundation of data communication on the World Wide Web.", "tech", Difficulty.MEDIUM, 15),
        Question(12, "Which company developed the Android operating system?", listOf("Apple", "Microsoft", "Google", "Samsung"), 2, "Google acquired Android Inc. in 2005 and developed the Android OS.", "tech", Difficulty.EASY, 10),
        Question(13, "What does 'CPU' stand for?", listOf("Central Processing Unit", "Computer Processing Unit", "Central Program Unit", "Core Processing Unit"), 0, "The CPU is the primary component of a computer that performs instructions.", "tech", Difficulty.EASY, 10),
        Question(14, "Which programming language was created by Guido van Rossum?", listOf("Java", "Python", "Ruby", "Kotlin"), 1, "Python was created by Guido van Rossum and released in 1991.", "tech", Difficulty.MEDIUM, 15),
        Question(15, "What is the binary representation of the decimal number 10?", listOf("1010", "1100", "1001", "0110"), 0, "10 in binary is 1010 (8+2=10).", "tech", Difficulty.HARD, 20),
        Question(16, "What does 'RAM' stand for?", listOf("Read Access Memory", "Random Access Memory", "Rapid Access Memory", "Read And Modify"), 1, "RAM is temporary memory that stores data currently being used by programs.", "tech", Difficulty.EASY, 10),
        Question(17, "Which protocol is used for sending emails?", listOf("FTP", "HTTP", "SMTP", "SSH"), 2, "SMTP (Simple Mail Transfer Protocol) is used for sending emails.", "tech", Difficulty.MEDIUM, 15),
        Question(18, "What is the full form of 'AI'?", listOf("Automated Intelligence", "Artificial Intelligence", "Advanced Integration", "Algorithmic Interface"), 1, "AI refers to the simulation of human intelligence in computer systems.", "tech", Difficulty.EASY, 10),
        Question(19, "Which data structure uses LIFO order?", listOf("Queue", "Array", "Stack", "Tree"), 2, "Stack uses Last In, First Out (LIFO) order.", "tech", Difficulty.HARD, 20),
        Question(20, "What year was the first iPhone released?", listOf("2005", "2006", "2007", "2008"), 2, "Apple released the first iPhone on June 29, 2007.", "tech", Difficulty.MEDIUM, 15)
    )

    private val historyQuestions = listOf(
        Question(21, "Who was the first President of the United States?", listOf("Thomas Jefferson", "John Adams", "George Washington", "Benjamin Franklin"), 2, "George Washington served as the 1st President from 1789 to 1797.", "history", Difficulty.EASY, 10),
        Question(22, "In which year did World War II end?", listOf("1943", "1944", "1945", "1946"), 2, "World War II ended in 1945 with Germany's surrender in May and Japan's in September.", "history", Difficulty.EASY, 10),
        Question(23, "Who built the Great Wall of China?", listOf("Ming Dynasty", "Han Dynasty", "Qin Dynasty", "Multiple Dynasties"), 3, "The Great Wall was built over many centuries by multiple Chinese dynasties.", "history", Difficulty.MEDIUM, 15),
        Question(24, "The Renaissance period began in which country?", listOf("France", "England", "Spain", "Italy"), 3, "The Renaissance began in Italy in the 14th century.", "history", Difficulty.MEDIUM, 15),
        Question(25, "Who wrote the Declaration of Independence?", listOf("George Washington", "Thomas Jefferson", "Benjamin Franklin", "John Adams"), 1, "Thomas Jefferson was the primary author of the Declaration of Independence in 1776.", "history", Difficulty.EASY, 10),
        Question(26, "Which ancient wonder was located in Alexandria?", listOf("Colossus of Rhodes", "Statue of Zeus", "Lighthouse of Alexandria", "Hanging Gardens"), 2, "The Lighthouse of Alexandria was one of the Seven Wonders of the Ancient World.", "history", Difficulty.HARD, 20),
        Question(27, "The French Revolution began in which year?", listOf("1776", "1787", "1789", "1792"), 2, "The French Revolution began in 1789 with the storming of the Bastille.", "history", Difficulty.MEDIUM, 15),
        Question(28, "Who was Cleopatra the ruler of?", listOf("Rome", "Greece", "Egypt", "Persia"), 2, "Cleopatra was the last active ruler of the Ptolemaic Kingdom of Egypt.", "history", Difficulty.EASY, 10),
        Question(29, "The Berlin Wall fell in which year?", listOf("1987", "1988", "1989", "1990"), 2, "The Berlin Wall fell on November 9, 1989.", "history", Difficulty.MEDIUM, 15),
        Question(30, "Which empire was ruled by Genghis Khan?", listOf("Ottoman Empire", "Roman Empire", "Mongol Empire", "Persian Empire"), 2, "Genghis Khan founded and ruled the Mongol Empire, the largest contiguous empire.", "history", Difficulty.MEDIUM, 15)
    )

    private val geographyQuestions = listOf(
        Question(31, "What is the capital of Australia?", listOf("Sydney", "Melbourne", "Canberra", "Brisbane"), 2, "Canberra is the capital of Australia, not Sydney as many people think.", "geography", Difficulty.MEDIUM, 15),
        Question(32, "Which is the longest river in the world?", listOf("Amazon", "Mississippi", "Nile", "Yangtze"), 2, "The Nile River in Africa is generally considered the longest river at 6,650 km.", "geography", Difficulty.EASY, 10),
        Question(33, "Mount Everest is located in which mountain range?", listOf("Alps", "Andes", "Himalayas", "Rockies"), 2, "Mount Everest is the highest peak in the Himalayas and on Earth.", "geography", Difficulty.EASY, 10),
        Question(34, "What is the smallest country in the world?", listOf("Monaco", "Vatican City", "San Marino", "Liechtenstein"), 1, "Vatican City is the world's smallest country at about 0.44 square kilometers.", "geography", Difficulty.MEDIUM, 15),
        Question(35, "Which ocean is the largest?", listOf("Atlantic", "Indian", "Arctic", "Pacific"), 3, "The Pacific Ocean is the largest and deepest ocean, covering over 165 million km².", "geography", Difficulty.EASY, 10),
        Question(36, "What is the capital of Japan?", listOf("Osaka", "Kyoto", "Tokyo", "Hiroshima"), 2, "Tokyo is the capital and most populous city of Japan.", "geography", Difficulty.EASY, 10),
        Question(37, "How many continents are there on Earth?", listOf("5", "6", "7", "8"), 2, "There are 7 continents: Africa, Antarctica, Asia, Australia, Europe, North America, South America.", "geography", Difficulty.EASY, 10),
        Question(38, "Which desert is the largest in the world?", listOf("Gobi", "Sahara", "Arabian", "Antarctic"), 3, "The Antarctic Desert is the largest desert, bigger than the Sahara.", "geography", Difficulty.HARD, 20),
        Question(39, "What is the capital of Brazil?", listOf("Rio de Janeiro", "São Paulo", "Brasília", "Salvador"), 2, "Brasília is the capital of Brazil, not Rio de Janeiro.", "geography", Difficulty.MEDIUM, 15),
        Question(40, "Which country has the most time zones?", listOf("Russia", "USA", "China", "France"), 3, "France has 12 time zones due to its overseas territories.", "geography", Difficulty.HARD, 20)
    )

    private val sportsQuestions = listOf(
        Question(41, "How many players are on a standard football (soccer) team?", listOf("9", "10", "11", "12"), 2, "A standard football team has 11 players including the goalkeeper.", "sports", Difficulty.EASY, 10),
        Question(42, "How many rings are on the Olympic flag?", listOf("4", "5", "6", "7"), 1, "The Olympic flag has 5 rings representing the 5 continents of the world.", "sports", Difficulty.EASY, 10),
        Question(43, "Which country won the first FIFA World Cup in 1930?", listOf("Brazil", "Argentina", "Uruguay", "Italy"), 2, "Uruguay won the first FIFA World Cup held on home soil in 1930.", "sports", Difficulty.MEDIUM, 15),
        Question(44, "In which sport would you perform a 'slam dunk'?", listOf("Volleyball", "Football", "Basketball", "Baseball"), 2, "A slam dunk is a shot in basketball where the player jumps and dunks the ball.", "sports", Difficulty.EASY, 10),
        Question(45, "How long is a marathon race?", listOf("40 km", "42.195 km", "44 km", "45 km"), 1, "A marathon is exactly 42.195 kilometers (26.219 miles) long.", "sports", Difficulty.MEDIUM, 15),
        Question(46, "Which sport uses a shuttlecock?", listOf("Tennis", "Squash", "Badminton", "Pickleball"), 2, "Badminton uses a shuttlecock (also called a birdie) instead of a ball.", "sports", Difficulty.EASY, 10),
        Question(47, "How many holes are there in a standard golf course?", listOf("9", "12", "18", "24"), 2, "A standard golf course has 18 holes.", "sports", Difficulty.EASY, 10),
        Question(48, "Which country has won the most Olympic gold medals overall?", listOf("Russia", "China", "Great Britain", "United States"), 3, "The United States has won the most Olympic gold medals in history.", "sports", Difficulty.MEDIUM, 15),
        Question(49, "What is the maximum score in a single bowling frame?", listOf("10", "20", "30", "25"), 2, "A perfect strike in bowling gives 10 pins, but with bonus pins, a frame can score 30.", "sports", Difficulty.HARD, 20),
        Question(50, "In tennis, what is the term for a score of zero?", listOf("Nil", "Zero", "Love", "Blank"), 2, "In tennis, zero points is called 'Love', possibly from the French word 'l'oeuf' (egg).", "sports", Difficulty.MEDIUM, 15)
    )

    private val mathQuestions = listOf(
        Question(51, "What is the value of π (Pi) to two decimal places?", listOf("3.14", "3.41", "3.12", "3.16"), 0, "Pi (π) is approximately 3.14159..., rounded to 3.14.", "math", Difficulty.EASY, 10),
        Question(52, "What is the square root of 144?", listOf("11", "12", "13", "14"), 1, "√144 = 12, because 12 × 12 = 144.", "math", Difficulty.EASY, 10),
        Question(53, "If a triangle has angles of 60° and 80°, what is the third angle?", listOf("30°", "40°", "50°", "60°"), 1, "The sum of angles in a triangle is 180°. 180 - 60 - 80 = 40°.", "math", Difficulty.MEDIUM, 15),
        Question(54, "What is 15% of 200?", listOf("20", "25", "30", "35"), 2, "15% of 200 = (15/100) × 200 = 30.", "math", Difficulty.MEDIUM, 15),
        Question(55, "What is the next prime number after 11?", listOf("12", "13", "14", "15"), 1, "13 is a prime number (divisible only by 1 and itself).", "math", Difficulty.MEDIUM, 15),
        Question(56, "What is 2⁸ (2 to the power of 8)?", listOf("128", "256", "512", "64"), 1, "2⁸ = 2×2×2×2×2×2×2×2 = 256.", "math", Difficulty.HARD, 20),
        Question(57, "What is the area of a circle with radius 7? (Use π ≈ 22/7)", listOf("144", "154", "164", "174"), 1, "Area = πr² = (22/7) × 7 × 7 = 22 × 7 = 154.", "math", Difficulty.HARD, 20),
        Question(58, "Solve: 3x + 12 = 30. What is x?", listOf("4", "5", "6", "7"), 2, "3x = 30 - 12 = 18, so x = 18/3 = 6.", "math", Difficulty.MEDIUM, 15),
        Question(59, "What is the Fibonacci sequence?", listOf("1,1,2,3,5,8", "1,2,3,4,5,6", "1,1,2,4,8,16", "2,4,6,8,10"), 0, "Each Fibonacci number is the sum of the two preceding ones.", "math", Difficulty.HARD, 20),
        Question(60, "What is log₁₀(1000)?", listOf("2", "3", "4", "10"), 1, "log₁₀(1000) = log₁₀(10³) = 3.", "math", Difficulty.HARD, 20)
    )

    fun getQuestionsForCategory(categoryId: String): List<Question> {
        return when (categoryId) {
            "science" -> scienceQuestions
            "tech" -> techQuestions
            "history" -> historyQuestions
            "geography" -> geographyQuestions
            "sports" -> sportsQuestions
            "math" -> mathQuestions
            else -> emptyList()
        }.shuffled()
    }
}
