# Bouldering Tracker Frontend
This is the React-based frontend for the Bouldering Tracker Web Application. It provides an interactive, mobile-responsive interface for bouldering enthusiasts to log, view, and share climbs. The frontend integrates with the backend API for authentication, climb management, and location-based filtering.

## Tech Stack
- React 18 with functional components and hooks
- React Router for navigation
- Context API for global state (Auth, Filters, Theme)
- TailwindCSS for styling
- Heroicons for UI icons
- Axios for API calls

## Key Features
- Public & Private Views
    - Public Page: Browse shared climbs from all users.
    - Private Page: Manage your own climbs (add, edit, delete).
- User Authentication
    - Login and registration forms.
- Climb Management
    - Add new climbs with details (grade, features, location, photo).
    - Edit or delete climbs from your private list.
- Filter System
    - Filter climbs by location, country, region, or GPS range.
    - Location autocomplete powered by backend search.
- Responsive Design
    - Optimized for mobile and desktop.
- Dark Mode Support
    - Controlled via ThemeContext.

## Future Enhancements
- Analytics Dashboard: Skill progression charts.
- Offline Support: Cache climbs for offline viewing.
- Social Features: Likes, comments on shared climbs.

## Wireframe
media/wireframe.pdf