'use client';

import { useEffect, useState } from 'react';
import { fetchMyChat } from '@/app/api/chat';
import ChatPreview from './ChatPreview';
import SubHeader from '../layout/header/SubHeader';
import ChatPreviewSkeleton from './ChatPreviewSkeleton';

type Props = {
  id: string;
  title: string;
  lastMessage: string;
  lastMessageAt: string;
  unreadCount: number;
};

export default function ChatMyList() {
  const [chats, setChats] = useState<Props[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const skeletonCount = 10;

  useEffect(() => {
    const loadChats = async () => {
      try {
        const chatList = await fetchMyChat();
        const mappedChats = chatList.map((chat: any) => ({
          id: chat.id.toString(),
          title: chat.title,
          lastMessage: chat.lastMessage ?? '대화를 시작해보세요.',
          unreadCount: chat.unreadCount ?? 0,
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
        setChats(mappedChats);
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
      <SubHeader text='나의 채팅' />
      <section className='h-full overflow-y-auto pb-16'>
        {error ? (
          <p className='text-center text-red-500 mt-4'>
            채팅 목록을 불러올 수 없습니다.
          </p>
        ) : loading ? (
          Array.from({ length: skeletonCount }).map((_, i) => (
            <ChatPreviewSkeleton key={i} />
          ))
        ) : chats.length === 0 ? (
          <p className='text-center text-gray-500 mt-4'>
            참여한 채팅방이 없습니다.
          </p>
        ) : (
          chats.map((chat) => (
            <ChatPreview
              key={chat.id}
              id={chat.id}
              title={chat.title}
              lastMessage={chat.lastMessage}
              lastMessageAt={chat.lastMessageAt}
              unreadCount={chat.unreadCount}
            />
          ))
        )}
      </section>
    </>
  );
}