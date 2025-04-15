'use client';
import Button from '@/components/common/Button';

type Props = {
  mainText: string;
  buttonText: string;
  onClick?: () => void;
};

export default function SelectHeader({
  mainText = '메인 텍스트',
  buttonText = '버튼',
  onClick,
}: Props) {
  return (
    <header className='sticky top-0 rounded-2xl z-50 flex w-full items-center justify-center border-b border-border bg-white px-4 py-[16.5px]'>
      <h1 className='text-xl font-bold text-black'>{mainText}</h1>
      <Button
        className='absolute right-4 h-8 w-1/5 font-medium bg-green-700 px-3 rounded-full flex items-center justify-center'
        onClick={onClick}
      >
        {buttonText}
      </Button>
    </header>
  );
}