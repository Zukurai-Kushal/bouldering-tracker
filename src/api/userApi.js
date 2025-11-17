import api from './axiosConfig';

// Login using Spring Security form login
export const login = (username, password) =>
  api.post('/login', new URLSearchParams({ username, password }));

// Logout
export const logout = () => api.post('/logout');

// Get current user info
export const getCurrentUser = () => api.get('/users/me');

// Get all climbs for current user
export const getUserClimbs = () => api.get('/users/me/climbs');

// Search climbs for current user with filters
export const searchUserClimbs = (params) =>
  api.get('/users/me/climbs/search', { params });

// Create a new climb
export const createClimb = (data) => api.post('/users/me/climbs', data);

// Update an existing climb
export const updateClimb = (climbId, data) => api.put(`/users/me/climbs/${climbId}`, data);

export const createFeatureTag = (data) => api.post('/users/me/feature-tags', data);