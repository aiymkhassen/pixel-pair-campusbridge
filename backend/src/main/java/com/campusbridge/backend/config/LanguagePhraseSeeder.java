package com.campusbridge.backend.config;

import com.campusbridge.backend.entity.LanguagePhrase;
import com.campusbridge.backend.repository.LanguagePhraseRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LanguagePhraseSeeder implements CommandLineRunner {

    private final LanguagePhraseRepository repository;

    public LanguagePhraseSeeder(
            LanguagePhraseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            return;
        }

        List<LanguagePhrase> phrases = new ArrayList<>();

        phrases.add(phrase("Greetings",
            "Hello", "Сәлем", "Привет"));

        phrases.add(phrase("Greetings",
            "Good morning", "Қайырлы таң", "Доброе утро"));

        phrases.add(phrase("Greetings",
            "Good afternoon", "Қайырлы күн", "Добрый день"));

        phrases.add(phrase("Greetings",
            "Good evening", "Қайырлы кеш", "Добрый вечер"));

        phrases.add(phrase("Greetings",
            "Goodbye", "Сау болыңыз", "До свидания"));

        phrases.add(phrase("Greetings",
            "Thank you", "Рақмет", "Спасибо"));

        phrases.add(phrase("Greetings",
            "Please", "Өтінемін", "Пожалуйста"));

        phrases.add(phrase("Greetings",
            "How are you?", "Қалыңыз қалай?", "Как дела?"));

        phrases.add(phrase("Greetings",
            "Nice to meet you", "Танысқаныма қуаныштымын",
            "Приятно познакомиться"));

        phrases.add(phrase("Greetings",
            "Excuse me", "Кешіріңіз", "Извините"));

        phrases.add(phrase("University",
            "Where is the library?", "Кітапхана қайда?",
            "Где библиотека?"));

        phrases.add(phrase("University",
            "Where is the classroom?", "Аудитория қайда?",
            "Где аудитория?"));

        phrases.add(phrase("University",
            "I am a student", "Мен студентпін",
            "Я студент"));

        phrases.add(phrase("University",
            "I don't understand", "Мен түсінбеймін",
            "Я не понимаю"));

        phrases.add(phrase("University",
            "Can you explain?", "Түсіндіріп бере аласыз ба?",
            "Можете объяснить?"));

        phrases.add(phrase("University",
            "When is the exam?", "Емтихан қашан?",
            "Когда экзамен?"));

        phrases.add(phrase("University",
            "I need help", "Маған көмек керек",
            "Мне нужна помощь"));

        phrases.add(phrase("University",
            "Where is the cafeteria?", "Асхана қайда?",
            "Где столовая?"));

        phrases.add(phrase("University",
            "What is the homework?", "Үй тапсырмасы қандай?",
            "Какое домашнее задание?"));

        phrases.add(phrase("University",
            "Can I ask a question?", "Сұрақ қойсам бола ма?",
            "Можно задать вопрос?"));

        phrases.add(phrase("Shopping",
            "How much does it cost?", "Қанша тұрады?",
            "Сколько стоит?"));

        phrases.add(phrase("Shopping",
            "Too expensive", "Тым қымбат", "Слишком дорого"));

        phrases.add(phrase("Shopping",
            "Can I pay by card?", "Картамен төлей аламын ба?",
            "Можно оплатить картой?"));

        phrases.add(phrase("Shopping",
            "I want this", "Мен мынаны алғым келеді",
            "Я хочу это"));

        phrases.add(phrase("Shopping",
            "Do you have a bag?", "Пакет бар ма?",
            "У вас есть пакет?"));

        phrases.add(phrase("Shopping",
            "Where is the supermarket?", "Супермаркет қайда?",
            "Где супермаркет?"));

        phrases.add(phrase("Shopping",
            "Cash", "Қолма-қол ақша", "Наличные"));

        phrases.add(phrase("Shopping",
            "Receipt", "Түбіртек", "Чек"));

        phrases.add(phrase("Shopping",
            "Cheap", "Арзан", "Дешёвый"));

        phrases.add(phrase("Shopping",
            "I am just looking", "Мен жай қарап жүрмін",
            "Я просто смотрю"));

        phrases.add(phrase("Transportation",
            "Where is the bus stop?", "Аялдама қайда?",
            "Где остановка?"));

        phrases.add(phrase("Transportation",
            "Bus", "Автобус", "Автобус"));

        phrases.add(phrase("Transportation",
            "Taxi", "Такси", "Такси"));

        phrases.add(phrase("Transportation",
            "Stop here, please", "Осы жерге тоқтаңызшы",
            "Остановитесь здесь, пожалуйста"));

        phrases.add(phrase("Transportation",
            "How do I get there?", "Ол жерге қалай барамын?",
            "Как мне туда добраться?"));

        phrases.add(phrase("Transportation",
            "Turn left", "Солға бұрылыңыз",
            "Поверните налево"));

        phrases.add(phrase("Transportation",
            "Turn right", "Оңға бұрылыңыз",
            "Поверните направо"));

        phrases.add(phrase("Transportation",
            "Go straight", "Тура жүріңіз",
            "Идите прямо"));

        phrases.add(phrase("Transportation",
            "Is it far?", "Алыс па?", "Это далеко?"));

        phrases.add(phrase("Transportation",
            "How much is the fare?", "Жолақы қанша?",
            "Сколько стоит проезд?"));

        phrases.add(phrase("Food",
            "Water", "Су", "Вода"));

        phrases.add(phrase("Food",
            "Tea", "Шай", "Чай"));

        phrases.add(phrase("Food",
            "Bread", "Нан", "Хлеб"));

        phrases.add(phrase("Food",
            "Chicken", "Тауық еті", "Курица"));

        phrases.add(phrase("Food",
            "Is this halal?", "Бұл халал ма?",
            "Это халяль?"));

        phrases.add(phrase("Food",
            "I don't eat pork", "Мен шошқа етін жемеймін",
            "Я не ем свинину"));

        phrases.add(phrase("Food",
            "Menu, please", "Мәзір беріңізші",
            "Меню, пожалуйста"));

        phrases.add(phrase("Food",
            "The bill, please", "Есепшот беріңізші",
            "Счёт, пожалуйста"));

        phrases.add(phrase("Food",
            "Very delicious", "Өте дәмді", "Очень вкусно"));

        phrases.add(phrase("Food",
            "I am hungry", "Қарным ашты",
            "Я хочу есть"));

        phrases.add(phrase("Emergencies",
            "Help me, please", "Көмектесіңізші",
            "Помогите, пожалуйста"));

        phrases.add(phrase("Emergencies",
            "Call an ambulance", "Жедел жәрдем шақырыңыз",
            "Вызовите скорую помощь"));

        phrases.add(phrase("Emergencies",
            "Call the police", "Полиция шақырыңыз",
            "Вызовите полицию"));

        phrases.add(phrase("Emergencies",
            "I am lost", "Мен адасып қалдым",
            "Я потерялся"));

        phrases.add(phrase("Emergencies",
            "Where is the hospital?", "Аурухана қайда?",
            "Где больница?"));

        phrases.add(phrase("Emergencies",
            "I feel sick", "Өзімді нашар сезініп тұрмын",
            "Мне плохо"));

        phrases.add(phrase("Emergencies",
            "Where is the pharmacy?", "Дәріхана қайда?",
            "Где аптека?"));

        phrases.add(phrase("Emergencies",
            "I lost my phone", "Телефонымды жоғалтып алдым",
            "Я потерял телефон"));

        phrases.add(phrase("Emergencies",
            "I need a doctor", "Маған дәрігер керек",
            "Мне нужен врач"));

        phrases.add(phrase("Emergencies",
            "Do you speak English?", "Ағылшынша сөйлейсіз бе?",
            "Вы говорите по-английски?"));

        repository.saveAll(phrases);

        System.out.println(
            "Language Corner: " + phrases.size()
            + " phrases added successfully.");
    }

    private LanguagePhrase phrase(
            String category,
            String english,
            String kazakh,
            String russian) {

        LanguagePhrase phrase = new LanguagePhrase();

        phrase.setCategory(category);
        phrase.setEnglish(english);
        phrase.setKazakh(kazakh);
        phrase.setRussian(russian);

        return phrase;
    }
}
