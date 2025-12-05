import { useContext } from 'react';
import { FunnelIcon, Cog6ToothIcon, PlusIcon } from '@heroicons/react/24/outline';
import { AuthContext } from '../../context/AuthContext';
import { ThemeContext } from '../../context/ThemeContext';

export default function HeaderBar({
  filters,
  onFilterClick,
  onSettingsClick,
  showSettings,
  children
}) {
  const { user } = useContext(AuthContext);
  const { darkMode } = useContext(ThemeContext);

  return (
    <div className="flex flex-col gap-2 mb-4">
      {/* Top Row: Buttons */}
      <div className="flex justify-between items-center">
        {/* Left Buttons */}
        <div className="flex gap-2">
          {/* Filter Button */}
          <button
            onClick={onFilterClick}
            className="flex items-center gap-1 bg-blue-500 text-white px-3 py-2 rounded-md hover:bg-blue-600"
          >
            <FunnelIcon className="h-5 w-5" /> Filter
          </button>

          {/* Add Climb Button (only if logged in) */}
          {user && (
            <button
              onClick={() => children && children.onAddClimb()}
              className="flex items-center gap-1 bg-green-500 text-white px-3 py-2 rounded-md hover:bg-green-600"
            >
              <PlusIcon className="h-5 w-5" /> Add Climb
            </button>
          )}
        </div>

        {/* Right Button: Settings */}
        <button
          onClick={onSettingsClick}
          className="flex items-center gap-1 bg-gray-200 text-white px-3 py-2 rounded-md hover:bg-gray-300"
        >
          <Cog6ToothIcon className="h-5 w-5" /> Settings
        </button>
      </div>

      {/* Filter Chips */}
      <div className="flex flex-wrap gap-2 mt-2">
        {filters.locationName && (
          <span className="bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-100 px-2 py-1 rounded-full text-sm">
            Location: {filters.locationName}
          </span>
        )}
        {filters.country && (
          <span className="bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-100 px-2 py-1 rounded-full text-sm">
            Country: {filters.country}
          </span>
        )}
        {filters.region && (
          <span className="bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-100 px-2 py-1 rounded-full text-sm">
            Region: {filters.region}
          </span>
        )}
        {(filters.latStart && filters.latEnd && filters.longStart && filters.longEnd) && (
          <span className="bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-100 px-2 py-1 rounded-full text-sm">
            GPS Range
          </span>
        )}
      </div>
    </div>
  );
}