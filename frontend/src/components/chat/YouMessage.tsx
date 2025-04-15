'use client';

type Props = {
  message: string;
  time: string;
  nickname: string;
};

export default function YouMessage({ message, time, nickname }: Props) {
  return (
    <div className="mb-2 flex flex-col items-start">
      <span className="text-sm text-gray-700 font-semibold pl-1">{nickname}</span>
      <div className="rounded-xl bg-gray-200 px-4 py-2 text-sm text-black max-w-[80%]">
        {message}
      </div>
      <span className="text-[12px] text-gray-400 pl-1">{time}</span>
    </div>
  );
}