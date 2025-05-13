'use client';

import { useState } from 'react';
import Input from '@/components/common/Input';
import {IoMdSend} from 'react-icons/io';
import CircleButton from '../common/CircleButton';
import PlusIcon from '../icon/PlusIcon';

export default function Inputbar() {
  const [input, setInput] = useState('');

  const handleSend = () => {
    if (!input.trim()) return;
    setInput('');
  };

  return (
    <div className="fixed bottom-0 left-1/2 z-40 flex h-16 w-full max-w-[430px] -translate-x-1/2  items-center justify-between gap-2 border-t bg-white px-2 py-2">
      <CircleButton size={'sm'} className='p-2'><PlusIcon size={'sm'}/></CircleButton>
      <Input
        type="text"
        placeholder="메세지를 입력하세요"
        className="text-base border border-green-700 rounded-3xl px-4"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        onKeyDown={(e) => {
          if (e.key === 'Enter') handleSend();
        }}
      />
      <button
        onClick={handleSend}
        className="text-green-700"
      >
        <IoMdSend size={40}/>
      </button>
    </div>
  );
}