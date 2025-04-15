'use client';

import { useRouter } from 'next/navigation';
import { useState } from 'react';
import BackHeader from '@/components/layout/header/BackHeader';
import Input from '@/components/common/Input';
import Button from '@/components/common/Button';

export default function ChatCreate() {
  const router = useRouter();
  const [roomName, setRoomName] = useState('');
  const [content, setContent] = useState('');

  const handleBack = () => {
    router.back(); // 이전 페이지로 돌아감
  };

  const onCreate = () => {
    if (!roomName.trim() || !content.trim()) return;
    router.push('/chat/123');
  };

  return (
    <>
      <BackHeader mainText="채팅방 생성" onClick={handleBack} />
      <section className="h-full overflow-y-auto px-4 py-1">
        <form className="pt-2 flex flex-col gap-3" onClick={onCreate}>
            <label className="block text-sm font-semibold text-gray-700">
              채팅방 이름
            </label>
            <Input
              className='border'
              type="text"
              value={roomName}
              onChange={(e) => setRoomName(e.target.value)}
              placeholder="채팅방 이름을 입력하세요"
            />
          <label className="block text-sm font-semibold text-gray-700">
            소개
          </label>
         <textarea name='content' rows={3} onChange={(e) => setContent(e.target.value)}
                   className='border py-1 px-2 text-sm font-semibold text-gray-700 focus:outline-none'/>
          <Button
            type="submit"
            disabled={!roomName.trim()}
            className='disabled:bg-gray-300 h-12'
          >
            채팅방 생성하기
          </Button>
        </form>
      </section>
    </>
  );
}