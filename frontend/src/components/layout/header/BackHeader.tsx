'use client';
import Button from '@/components/common/Button';
import { IoChevronBack } from 'react-icons/io5';

type Props = {
  mainText: string;
  onClick?: () => void;
};

export default function BackHeader({
  mainText = '메인 텍스트',
  onClick,
}: Props) {
  return (
    <header className="sticky top-0 z-50 flex w-full items-center justify-center border-b border-gray-300 bg-white px-4 py-[16.5px] transition-colors duration-500">
      <Button
        className="absolute left-1 h-8 w-1/6 font-medium bg-white  rounded-full flex items-center justify-center"
        onClick={onClick}
      >
        <IoChevronBack size={35} color="black" />
      </Button>
      <h1 className="text-xl font-bold text-black ">{mainText}</h1>
    </header>
  );
}