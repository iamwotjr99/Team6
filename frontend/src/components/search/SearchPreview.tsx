'use client';

import { useModalStore } from '@/stores/Modal';
import JoinModal from '../chat/JoinModal';


type Props = {
  id: string;
  title: string;
  lastMessage: string;
  lastMessageAt: string;
  unreadCount?: number;
};

export default function SearchPreview({
  id,
  title,
  lastMessage,
  lastMessageAt,
  unreadCount,
}: Props) {
  const setModal = useModalStore.use.setModal();
  const setSelectedChat = useModalStore.use.setSelectedChat();

  const handleClick = () => {
    setSelectedChat({ id, title });
    setModal(<JoinModal />);
  };

  return (
    <div
      onClick={handleClick}
      className='block cursor-pointer px-4 py-2.5 border-b border-gray-200'
    >
      <div className='flex justify-between items-center mb-1'>
        <h2 className='text-lg font-semibold text-gray-800'>{title}</h2>
        <span className='text-sm text-gray-400'>{lastMessageAt}</span>
      </div>
      <div className='flex justify-between items-center'>
        <p className='text-base text-gray-600 truncate'>{lastMessage}</p>
        {typeof unreadCount === 'number' && unreadCount > 0 && (
          <span className='text-xs text-white font-bold bg-green-600 px-2 rounded-full'>
            {unreadCount}
          </span>
        )}
      </div>
    </div>
  );
}