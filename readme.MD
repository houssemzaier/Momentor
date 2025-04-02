# Momentor

A social photo-sharing app that combines spontaneity with intentionality.

## Table of Contents
1. [Product Overview](#product-overview)
2. [Core Loop](#core-loop)
3. [Product Vision](#product-vision)
4. [Path to 1M DAU](#path-to-1m-daily-active-users)
5. [Technical Implementation](#technical-implementation)
6. [Features](#features)
7. [Architecture](#architecture)
8. [Development Notes](#development-notes)
9. [Setup Instructions](#setup-instructions)
10. [Time Investment](#time-investment)

## Product Overview

Momentor is a social photo-sharing application that transforms casual photography into intentional moments. Each day, users receive a random notification between 8 AM and 8 PM. When they open the app, a 5-second countdown begins before automatically capturing their photo. This creates a unique balance between authentic, in-the-moment content and thoughtfully composed images.

Users have a 30-minute window to respond to the notification. Photos taken within this window are marked as "on time," while later submissions are marked as "late," encouraging timely participation without preventing content creation.

## Core Loop

![Momentor Core Loop Diagram](art/core_loop_diagram.png)

The diagram illustrates how users interact with Momentor:
1. User receives a random notification between 8 AM and 8 PM
2. User has 30 minutes to respond to the notification
3. When opening the app, user faces a 5-second countdown before photo capture
4. Photo is automatically taken and can be shared to their feed
5. User can view and react to friends' daily moments
6. The consistent daily prompts create a habit, bringing users back daily

## Product Vision

### Job To Be Done
"When I want to stay connected with friends through authentic glimpses of daily life, Momentor gives me a daily prompt with a brief moment to compose my shot, so I can share more genuine moments without the pressure of perfect curation that exists on other platforms."

### Key Differentiators
* **Combined Spontaneity and Intention**: Unlike Instagram's simultaneous photos with no preparation time, Momentor gives users a brief window to compose their shot
* **Daily Ritual**: Creates a consistent habit without overwhelming users with constant content demands
* **Quality-Focused**: The countdown encourages more thoughtful composition than typical spontaneous captures
* **Authentic Community**: Built around genuine moments rather than heavily filtered or edited content

### Target Demographic
* Primary: 18-34 year olds who are digitally connected but tired of overly curated social media
* Secondary: People looking for simpler, more authentic ways to stay connected with friends and family

### Competitive Analysis

| App | Key Differentiator | Momentor Advantage |
|-----|--------------------|--------------------|
| Instagram | Highly curated, polished content | Authentic, in-the-moment sharing |
| Snapchat | Ephemeral content that disappears | Persistent but genuine moments |
| TikTok | Video-focused content creation | Simple photo sharing without editing pressure |

## Path to 1M Daily Active Users

### Feature Roadmap

#### Phase 1: MVP Launch (0-100K Users)
* **Core Experience**:
    - Daily random notification system
    - Countdown timer photo capture
    - Basic feed and profile functionality
    - Local storage of moments

* **Early Growth Features**:
    - Friend invitation system with personal referral codes
    - "Share to other platforms" functionality
    - Basic streaks to encourage daily use

#### Phase 2: Engagement Boost (100K-500K Users)
* **Social Enhancement**:
    - Reactions and comments on moments
    - Friend discovery through contacts
    - Group moments (shared experiences)
    - Weekly highlights of best moments

* **Retention Builders**:
    - Personalized notification times
    - Memories feature (flashbacks to past moments)
    - Consecutive day streaks with rewards

#### Phase 3: Viral Acceleration (500K-1M Users)
* **Network Effects**:
    - Collaborative moments collections
    - Moment challenges (themed prompts)
    - Public profiles for creators
    - "Best of Momentor" curated feed

* **Platform Expansion**:
    - iOS app launch
    - Web viewer for shared moments
    - API for third-party integrations

### Growth Strategy
Each phase optimizes a specific aspect of the user experience:

1. **Phase 1 (Months 1-3)**: Core experience and word-of-mouth acquisition
2. **Phase 2 (Months 4-6)**: Deepening social connections and habit formation
3. **Phase 3 (Months 7-12)**: Network effects and platform expansion

## Technical Implementation

This prototype demonstrates the core functionality of Momentor using modern Android development practices.

### Tech Stack
- **UI**: Jetpack Compose
- **Camera**: CameraX
- **Navigation**: Compose Navigation
- **Database**: Room
- **Scheduling**: WorkManager
- **Architecture**: MVVM with Clean Architecture principles
- **Language**: 100% Kotlin

## Features

Features are prioritized based on importance to the core concept and implementation complexity:

### Priority 1: Core Experience (MVP)
| Feature | Description | Implementation |
|---------|-------------|----------------|
| **Countdown Timer Camera** | 5-second countdown before auto-capturing photo | CameraX with custom overlay |
| **Photo Feed** | Timeline of moments from you and friends | Compose LazyColumn |
| **Local Storage** | Saves captured photos and feed data | Room database |

### Priority 2: Key Differentiators
| Feature | Description | Implementation |
|---------|-------------|----------------|
| **Random Daily Notification** | Notification sent at random time between 8AM-8PM | WorkManager scheduled task |
| **Late Indicator** | Shows if photo was taken within 30min window | Simple timestamp tracking |

### Priority 3: Enhanced Experience
| Feature | Description | Implementation |
|---------|-------------|----------------|
| **Basic Profile** | Shows user's past moments | Static implementation for prototype |
| **Like/React Functionality** | Simple interaction with friends' moments | UI components + local state |

## Architecture

Momentor follows a modern Android architecture:

```
├── ui/
│   ├── camera/       # Camera screen with countdown
│   ├── feed/         # Timeline of moments
│   ├── profile/      # User profile
│   └── theme/        # App styling
├── domain/
│   ├── model/        # Data models
│   └── usecase/      # Business logic
├── data/
│   ├── repository/   # Data access layer
│   ├── local/        # Room database
│   └── notification/ # Notification scheduler
└── di/               # Dependency injection
```

## Development Notes

### Key Technical Decisions

- **Jetpack Compose**: Chosen for rapid UI development and modern declarative approach to building interfaces
- **Local-First Approach**: Focused on local functionality for the prototype; cloud features would be added in production

### Significant Product Decisions
- **Fixed 5-second countdown**: Tested various durations; 5 seconds provides the right balance between spontaneity and composition time
- **30-minute response window**: Long enough to be flexible but short enough to maintain the time-sensitive concept
- **Random daily timing**: Creates unpredictability that cuts across different daily routines

### Implementation Challenges
- **Camera Preview Rotation**: Implemented custom transform to handle different device orientations
- **Countdown Animation**: Created custom animation using Compose's animation APIs
- **Notification Reliability**: Used WorkManager's guaranteed execution for reliable notification delivery

## Setup Instructions

1. Clone this repository
2. Open in Android Studio Arctic Fox or newer
3. Build and run on a device or emulator with API 24+


---

*This document outlines both the product vision and technical implementation for Momentor, a social photo-sharing app designed to reach 1M daily active users by creating a unique experience that balances authentic moments with intentional photography.*
