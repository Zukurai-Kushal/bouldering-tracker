import { useEffect, useState } from 'react';
import { createClimb, updateClimb, createFeatureTag } from '../../api/userApi';
import { getFeatureTags, searchLocations } from '../../api/publicApi';
import { XMarkIcon } from '@heroicons/react/24/outline';

export default function ClimbForm({ mode = 'create', initialData = {}, climbId, onClose, onSuccess }) {
  const [formData, setFormData] = useState({
    boulderName: initialData.boulderName || '',
    grade: initialData.grade || '',
    scale: initialData.scale || 'V_SCALE',
    rating: initialData.rating || '',
    attempts: initialData.attempts || '',
    comment: initialData.comment || '',
    photoURL: initialData.photoURL || '',
    shared: initialData.shared || false,
    locationId: initialData.location?.locationId || null,
    featureTagIds: initialData.features ? initialData.features.map(f => f.featureId) : []
  });

  const [loading, setLoading] = useState(false);
  const [successMsg, setSuccessMsg] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const [locationQuery, setLocationQuery] = useState(initialData.location?.name || '');
  const [locationResults, setLocationResults] = useState([]);

  const [featureTags, setFeatureTags] = useState([]);
  const [newFeatureTag, setNewFeatureTag] = useState('');

  // Fetch feature tags
  useEffect(() => {
    getFeatureTags().then(res => setFeatureTags(res.data)).catch(() => setFeatureTags([]));
  }, []);

  // Auto-search locations
  useEffect(() => {
    if (locationQuery.length > 1) {
      searchLocations(locationQuery)
        .then(res => setLocationResults(res.data))
        .catch(() => setLocationResults([]));
    } else {
      setLocationResults([]);
    }
  }, [locationQuery]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const validateForm = () => {
    if (!formData.scale) return 'Scale is required';
    if (formData.grade.length > 10) return 'Grade must be at most 10 characters';
    if (formData.boulderName.length > 100) return 'Boulder name must be at most 100 characters';
    if (formData.comment.length > 500) return 'Comment must be at most 500 characters';
    if (formData.photoURL.length > 255) return 'Photo URL must be at most 255 characters';
    if (formData.rating && (formData.rating < 0 || formData.rating > 3)) return 'Rating must be between 0 and 3';
    if (formData.attempts && formData.attempts < 0) return 'Attempts must be at least 0';
    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationError = validateForm();
    if (validationError) {
      setErrorMsg(validationError);
      return;
    }
    setLoading(true);
    setErrorMsg('');
    try {
      if (mode === 'edit') {
        await updateClimb(climbId, formData);
        setSuccessMsg('Climb updated successfully!');
      } else {
        await createClimb(formData);
        setSuccessMsg('Climb created successfully!');
      }
      setTimeout(() => {
        onSuccess();
      }, 1000);
    } catch {
      setErrorMsg('Failed to save climb. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleAddFeatureTag = async () => {
    if (!newFeatureTag.trim()) return;
    try {
      const res = await createFeatureTag({ name: newFeatureTag });
      setFeatureTags(prev => [...prev, res.data]);
      setNewFeatureTag('');
    } catch {
      alert('Failed to add feature tag');
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white dark:bg-gray-800 rounded-lg w-full max-w-md relative max-h-[90vh] flex flex-col">
        
        {/* Sticky Header */}
        <div className="sticky top-0 bg-white dark:bg-gray-800 p-4 border-b flex justify-between items-center z-10">
          <h2 className="text-lg font-semibold">{mode === 'edit' ? 'Edit Climb' : 'Add New Climb'}</h2>
          {errorMsg && <p className="text-red-500 mb-2">{errorMsg}</p>}
          {successMsg && <p className="text-green-500 mb-2">{successMsg}</p>}
          <button onClick={onClose} className="text-gray-500 hover:text-gray-700 dark:hover:text-gray-300">
            <XMarkIcon className="w-6 h-6" />
          </button>
        </div>

        {/* Scrollable Content */}
        <div className="overflow-y-auto p-4">

          <form onSubmit={handleSubmit} className="space-y-4">
            <input name="boulderName" value={formData.boulderName} onChange={handleChange} placeholder="Boulder Name" className="w-full border rounded px-3 py-2 dark:bg-gray-700" />
            <input name="grade" value={formData.grade} onChange={handleChange} placeholder="Grade" className="w-full border rounded px-3 py-2 dark:bg-gray-700" />
            <select name="scale" value={formData.scale} onChange={handleChange} className="w-full border rounded px-3 py-2 dark:bg-gray-700">
              <option value="V_SCALE">V Scale</option>
              <option value="FONT_SCALE">Font Scale</option>
            </select>
            <input type="number" name="rating" value={formData.rating} onChange={handleChange} placeholder="Rating (0-3)" className="w-full border rounded px-3 py-2 dark:bg-gray-700" />
            <input type="number" name="attempts" value={formData.attempts} onChange={handleChange} placeholder="Attempts (≥0)" className="w-full border rounded px-3 py-2 dark:bg-gray-700" />
            <textarea name="comment" value={formData.comment} onChange={handleChange} placeholder="Comment" className="w-full border rounded px-3 py-2 dark:bg-gray-700" />
            <input name="photoURL" value={formData.photoURL} onChange={handleChange} placeholder="Photo URL" className="w-full border rounded px-3 py-2 dark:bg-gray-700" />
            <label className="flex items-center gap-2">
              <input type="checkbox" name="shared" checked={formData.shared} onChange={handleChange} />
              Share publicly
            </label>

            {/* Location Search */}
            <div>
              <p className="mb-2">Location:</p>
              <input
                value={locationQuery}
                onChange={(e) => setLocationQuery(e.target.value)}
                placeholder="Search location..."
                className="w-full border rounded px-3 py-2 dark:bg-gray-700"
              />
              {locationResults.length > 0 && (
                <div className="border rounded mt-2 max-h-48 overflow-y-auto dark:bg-gray-700">
                  {locationResults.map(loc => (
                    <div
                      key={loc.locationId}
                      onClick={() => {
                        setFormData(prev => ({ ...prev, locationId: loc.locationId }));
                        setLocationQuery(loc.name);
                        setLocationResults([]);
                      }}
                      className="flex items-center p-2 hover:bg-gray-200 dark:hover:bg-gray-600 cursor-pointer"
                    >
                      {loc.locationPhotoUrl && (
                        <img src={loc.locationPhotoUrl} alt={loc.name} className="w-12 h-12 object-cover rounded mr-2" />
                      )}
                      <div>
                        <p className="font-semibold">{loc.name}</p>
                        <p className="text-sm">{loc.type}</p>
                        <p className="text-xs text-gray-500">{loc.country}, {loc.region}</p>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>

            {/* Feature Tags */}
            <div>
              <p className="mb-2">Features:</p>
              <div className="flex flex-wrap gap-2">
                {featureTags.map(tag => (
                  <label key={tag.featureId} className="flex items-center gap-1">
                    <input
                      type="checkbox"
                      checked={formData.featureTagIds.includes(tag.featureId)}
                      onChange={() => {
                        setFormData(prev => ({
                          ...prev,
                          featureTagIds: prev.featureTagIds.includes(tag.featureId)
                            ? prev.featureTagIds.filter(id => id !== tag.featureId)
                            : [...prev.featureTagIds, tag.featureId]
                        }));
                      }}
                    />
                    {tag.name}
                  </label>
                ))}
              </div>
              <div className="flex gap-2 mt-2">
                <input value={newFeatureTag} onChange={(e) => setNewFeatureTag(e.target.value)} placeholder="New feature tag" className="border rounded px-2 py-1 dark:bg-gray-700" />
                <button type="button" onClick={handleAddFeatureTag} className="bg-green-500 text-white px-3 py-1 rounded">Add</button>
              </div>
            </div>

            <button type="submit" disabled={loading} className="bg-green-500 text-white px-4 py-2 rounded w-full">
              {loading ? 'Saving...' : mode === 'edit' ? 'Update Climb' : 'Save Climb'}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}