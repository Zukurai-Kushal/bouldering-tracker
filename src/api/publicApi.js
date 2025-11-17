import api from './axiosConfig';

export const getSharedClimbs = () => api.get('/public/climbs');
export const searchSharedClimbs = (params) => api.get('/public/climbs/search', { params });
export const getFeatureTags = (name) => api.get('/public/feature-tags', { params: { name } });
export const searchLocations = (name) => api.get('/public/locations/search', { params: { name } });