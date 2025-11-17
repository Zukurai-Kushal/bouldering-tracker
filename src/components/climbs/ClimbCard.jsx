import { useState } from 'react';
import ClimbDetailsModal from './ClimbDetailsModal';
import { StarIcon } from '@heroicons/react/24/solid';

export default function ClimbCard({ climb , isPrivate , onEdit, onDeleteSuccess}) {
  const [showModal, setShowModal] = useState(false);

  return (
    <>
      <div
        className="flex bg-white dark:bg-gray-800 rounded-lg shadow p-2 cursor-pointer w-full max-w-5xl border border-transparent hover:border-blue-500"
        onClick={() => setShowModal(true)}
      >
        {/* LHS Thumbnail */}
        <div className="w-20 h-20 bg-gray-200 dark:bg-gray-700 rounded-md overflow-hidden">
          {climb.photoURL && (
            <img src={climb.photoURL} alt={climb.boulderName} className="w-full h-full object-cover" />
          )}
        </div>

        {/* RHS Details */}
        <div className="flex-1 ml-3">
          {/* Row 1 */}
          <div className="flex items-center justify-between">
            <span className="font-bold">{climb.grade}</span>
            <span>{climb.boulderName}</span>
            <div className="flex">
              {[...Array(climb.rating)].map((_, i) => (
                <StarIcon key={i} className="h-4 w-4 text-yellow-400" />
              ))}
            </div>
          </div>
          {/* Row 2 */}
          <div className="text-sm text-gray-600 dark:text-gray-400">
            {climb.username} @{climb.location?.name || 'No location'}
          </div>
          {/* Row 3 */}
          <div className="text-xs mt-1">
            Attempts: {climb.attempts}
            {climb.features.map(f => (
              <span key={f.name} className="bg-blue-100 dark:bg-blue-700 px-2 py-0.5 rounded-full ml-1">
                {f.name}
              </span>
            ))}
          </div>
        </div>
      </div>

        {showModal && (
          <ClimbDetailsModal
            climb={climb}
            onClose={() => setShowModal(false)}
            isPrivate={isPrivate}
            onEdit={() => onEdit(climb)}
            onDeleteSuccess={() => {
              setShowModal(false);
              onDeleteSuccess();
            }}
          />
        )}
    </>
  );
}