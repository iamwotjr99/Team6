'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { IoSearch } from 'react-icons/io5';
import { fetchWeChat } from '@/app/api/chat';
import ChatPreviewSkeleton from '../chat/ChatPreviewSkeleton';
import SearchPreview from './SearchPreview';

type Props = {
  id: string;
  title: string;
  lastMessage: string;
  lastMessageAt: string;
};

export default function ChatWeList() {
  const [chats, setChats] = useState<Props[]>([]);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const skeletonCount = 10; 

  useEffect(() => {
    const loadChats = async () => {
      try {
        const chatList = await fetchWeChat();

        const mapped = chatList.map((chat: any) => ({
          id: chat.id.toString(),
          title: chat.title,
          lastMessage: chat.lastMessage ?? '대화를 시작해보세요.',
          lastMessageAt: chat.lastMessageAt
            ? new Date(chat.lastMessageAt).toLocaleString('ko-KR', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit',
              })
            : '시간 정보 없음',
        }));

        setChats(mapped);
      } catch (err) {
        console.error(err);
        setError(true);
      } finally {
        setLoading(false);
      }
    };

    loadChats();
  }, []);

  return (
    <>
      <header className='sticky top-0 z-50 flex w-full items-center justify-center border-b border-border bg-white px-4 py-[16.5px]'>
        <h1 className='text-xl font-bold text-black'>모든 채팅</h1>
        <Link
          href='/chat/search'
          className='absolute right-2 h-12 w-1/6 font-medium bg-white px-3 rounded-full flex items-center justify-center'
        >
          <IoSearch size={30} color='black' />
        </Link>
      </header>

      <section className="h-full overflow-y-auto pb-16">
        {error ? (
          <p className="text-center text-red-500 mt-4">
            채팅 목록을 불러올 수 없습니다.
          </p>
        ) : loading ? (
          Array.from({ length: skeletonCount }).map((_, i) => (
            <ChatPreviewSkeleton key={i} />
          ))
        ) : chats.length === 0 ? (
          <p className="text-center text-gray-500 mt-4">
            참여할 수 있는 채팅방이 없습니다.
          </p>
        ) : (
          chats.map((chat) => (
            <SearchPreview
              key={chat.id}
              id={chat.id}
              title={chat.title}
              lastMessage={chat.lastMessage}
              lastMessageAt={chat.lastMessageAt}
            />
          ))
        )}
      </section>
    </>
  );
}