# CS-19300-Final-Project---Meal-Tracker
Java program to track macros of meals

## The Problem

I want to solve calorie, and eventually macro tracking custom recipes for foods or drinks. Many calorie trackers don't have it for free or make it clunky to use.

## How It Works

Welcomes user and then asks user whether they want to view meals or create a new one. Meals are stored in a .txt file, Meals.txt, which will be stored locally
If they want to view
 - reads a Meals.txt file 
     - if no file found, means no meals saved
 - outputs all the meals in the file and their calories
If they want to create:
 - Manual input:
     - Have user input meal/food name, calories, and eventually macros
     - create new food item
     - store food item in the Meals.txt file

<img width="942" height="492" alt="CS 19300 Final Project Flowchart drawio (3)" src="https://github.com/user-attachments/assets/4856a55d-02ae-40d0-b0ab-b4787be26a03" />




## Tech Stack

- **Language:** Java
- **Libraries:** IO, Util, http
- **External tools:** Food database API

## Setup

Clone Repository

```bash
git clone https://github.com/you/your-project
```

## Usage

Open repository folder in VS Code
 - VS Code
 - File -> Open Folder -> Open Cloned Repo

Click run


## Roadmap

- [ ] Include Macros such as protein, fats, carbs, and sugars
- [ ] Import files of saved meals
- [ ] Add welcome and goodbye messages

## Author

Kaden Chung, https://github.com/Kchung567
