package com.habittracker.service;

import com.habittracker.model.Habit;
import com.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HabitService {

    private final HabitRepository repo;

    public HabitService(HabitRepository repo) {
        this.repo = repo;
    }

    public List<Habit> getAll() {
        return repo.findAll();
    }

    public void addHabit(String name, String category) {
        Habit h = new Habit();
        h.setName(name);
        h.setCategory(category);
        h.setCurrentStreak(0);
        h.setLongestStreak(0);
        repo.save(h);
    }

    public void deleteHabit(Long id) {
        repo.deleteById(id);
    }

    public void markDone(Long id) {
        Habit h = repo.findById(id).orElse(null);

        if (h != null) {
            LocalDate today = LocalDate.now();

            if (h.getLastCompletedDate() != null &&
                h.getLastCompletedDate().equals(today.minusDays(1))) {
                h.setCurrentStreak(h.getCurrentStreak() + 1);
            } else {
                h.setCurrentStreak(1);
            }

            if (h.getCurrentStreak() > h.getLongestStreak()) {
                h.setLongestStreak(h.getCurrentStreak());
            }

            h.setLastCompletedDate(today);
            repo.save(h);
        }
    }
    public void resetStreak(Long id) {
    Habit h = repo.findById(id).orElse(null);
    if (h != null) {
        h.setCurrentStreak(0);
        repo.save(h);
    }
}
}
