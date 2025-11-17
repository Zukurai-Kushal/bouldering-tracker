import { useState, useEffect } from 'react';
import { searchLocations } from '../../api/publicApi';

export default function FilterModal({ onClose, onApply, currentFilters }) {
  const [locationQuery, setLocationQuery] = useState('');
  const [locationResults, setLocationResults] = useState([]);
  const [selectedLocation, setSelectedLocation] = useState(currentFilters.location || null);
  const [country, setCountry] = useState(currentFilters.country || '');
  const [region, setRegion] = useState(currentFilters.region || '');
  const [latStart, setLatStart] = useState(currentFilters.latStart || '');
  const [latEnd, setLatEnd] = useState(currentFilters.latEnd || '');
  const [longStart, setLongStart] = useState(currentFilters.longStart || '');
  const [longEnd, setLongEnd] = useState(currentFilters.longEnd || '');

  // Fetch locations as user types
  useEffect(() => {
    if (locationQuery.length > 1) {
      searchLocations(locationQuery).then(res => setLocationResults(res.data));
    } else {
      setLocationResults([]);
    }
  }, [locationQuery]);

  const handleApply = () => {
    onApply({
      locationId: selectedLocation?.locationId || null,
      locationName: selectedLocation?.name || '',
      country,
      region,
      latStart,
      latEnd,
      longStart,
      longEnd
    });
    onClose(); // Auto-close after apply
  };

  const handleReset = () => {
    setSelectedLocation(null);
    setCountry('');
    setRegion('');
    setLatStart('');
    setLatEnd('');
    setLongStart('');
    setLongEnd('');
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white dark:bg-gray-800 rounded-lg p-4 w-11/12 max-w-lg relative">
        <button onClick={onClose} className="absolute top-2 right-2 text-gray-500 hover:text-red-500">✕</button>
        <h2 className="text-lg font-bold mb-4">Filter Climbs</h2>

        {/* Location Search */}
        <div className="mb-4">
          <label className="block mb-1">Location Name</label>
          <input
            type="text"
            value={locationQuery}
            onChange={(e) => setLocationQuery(e.target.value)}
            className="w-full border rounded px-2 py-1 dark:bg-gray-700"
            placeholder="Search location..."
          />
          {locationResults.length > 0 && (
            <div className="mt-2 max-h-40 overflow-y-auto border rounded bg-gray-50 dark:bg-gray-700">
              {locationResults.map(loc => (
                <div
                  key={loc.locationId}
                  onClick={() => {
                    setSelectedLocation(loc);
                    setLocationQuery(loc.name);
                    setLocationResults([]);
                  }}
                  className="flex items-center p-2 hover:bg-gray-200 dark:hover:bg-gray-600 cursor-pointer"
                >
                  {/* LHS: Photo */}
                  <div className="w-16 h-16 bg-gray-200 dark:bg-gray-600 rounded overflow-hidden">
                    {loc.locationPhotoUrl && (
                      <img src={loc.locationPhotoUrl} alt={loc.name} className="w-full h-full object-cover" />
                    )}
                  </div>
                  {/* RHS: Details */}
                  <div className="ml-2 text-xs">
                    <div className="font-bold">{loc.name}</div>
                    <div>{loc.type}</div>
                    <div>{loc.country}, {loc.region}</div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Country & Region */}
        <div className="mb-4">
          <label className="block mb-1">Country</label>
          <input
            type="text"
            value={country}
            onChange={(e) => setCountry(e.target.value)}
            className="w-full border rounded px-2 py-1 dark:bg-gray-700"
          />
        </div>
        <div className="mb-4">
          <label className="block mb-1">Region</label>
          <input
            type="text"
            value={region}
            onChange={(e) => setRegion(e.target.value)}
            className="w-full border rounded px-2 py-1 dark:bg-gray-700"
          />
        </div>

        {/* GPS Range */}
        <div className="grid grid-cols-2 gap-2 mb-4">
          <div>
            <label className="block mb-1">Lat Start</label>
            <input
              type="number"
              value={latStart}
              onChange={(e) => setLatStart(e.target.value)}
              className="w-full border rounded px-2 py-1 dark:bg-gray-700"
            />
          </div>
          <div>
            <label className="block mb-1">Lat End</label>
            <input
              type="number"
              value={latEnd}
              onChange={(e) => setLatEnd(e.target.value)}
              className="w-full border rounded px-2 py-1 dark:bg-gray-700"
            />
          </div>
          <div>
            <label className="block mb-1">Long Start</label>
            <input
              type="number"
              value={longStart}
              onChange={(e) => setLongStart(e.target.value)}
              className="w-full border rounded px-2 py-1 dark:bg-gray-700"
            />
          </div>
          <div>
            <label className="block mb-1">Long End</label>
            <input
              type="number"
              value={longEnd}
              onChange={(e) => setLongEnd(e.target.value)}
              className="w-full border rounded px-2 py-1 dark:bg-gray-700"
            />
          </div>
        </div>

        {/* Buttons */}
        <div className="flex justify-between">
          <button
            onClick={handleReset}
            className="bg-gray-300 dark:bg-gray-600 px-3 py-2 rounded-md"
          >
            Reset
          </button>
          <button
            onClick={handleApply}
            className="bg-blue-500 text-white px-3 py-2 rounded-md"
          >
            Search
          </button>
        </div>
      </div>
    </div>
  );
}